package com.ibas.brta.vehims.configurations.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.configurations.model.VehicleType;
import com.ibas.brta.vehims.configurations.model.VehicleTypeClassMap;
import com.ibas.brta.vehims.configurations.payload.request.VehicleTypeRequest;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.configurations.repository.VehicleTypeClassMapRepository;
import com.ibas.brta.vehims.configurations.repository.VehicleTypeRepository;
import com.ibas.brta.vehims.exception.FieldValidationException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class VehicleTypeService {
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private VehicleTypeClassMapRepository vehicleTypeClassMapRepository;

    // Create or Insert operation
    @Transactional
    public VehicleType createData(VehicleTypeRequest request) {

        if (vehicleTypeRepository.existsByNameEn(request.getNameEn())) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Vehicle Type with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }

        VehicleType vehicleType = new VehicleType();
        BeanUtils.copyProperties(request, vehicleType);
        VehicleType savedData = vehicleTypeRepository.save(vehicleType);

        vehicleTypeClassMapRepository.deleteByVehicleTypeId(savedData.getId());

        for (Long vehicleClassId : request.getVehicleClassIds()) {
            VehicleTypeClassMap vehicleTypeClassMap = new VehicleTypeClassMap();
            vehicleTypeClassMap.setVehicleClassId(vehicleClassId);
            vehicleTypeClassMap.setVehicleTypeId(savedData.getId());
            vehicleTypeClassMapRepository.save(vehicleTypeClassMap);
        }

        return savedData;
    }

    // Update operation
    @Transactional
    public VehicleType updateData(Long id, VehicleTypeRequest request) {

        if (vehicleTypeRepository.existsByNameEnAndIdNot(request.getNameEn(), id)) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Vehicle Type with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }

        Optional<VehicleType> existingData = vehicleTypeRepository.findById(id);
        if (existingData.isPresent()) {
            VehicleType vehicleType = existingData.get();
            BeanUtils.copyProperties(request, vehicleType); // Exclude ID
            VehicleType savedData = vehicleTypeRepository.save(vehicleType);

            vehicleTypeClassMapRepository.deleteByVehicleTypeId(savedData.getId());

            for (Long vehicleClassId : request.getVehicleClassIds()) {
                VehicleTypeClassMap vehicleTypeClassMap = new VehicleTypeClassMap();
                vehicleTypeClassMap.setVehicleClassId(vehicleClassId);
                vehicleTypeClassMap.setVehicleTypeId(savedData.getId());
                vehicleTypeClassMapRepository.save(vehicleTypeClassMap);
            }

            return savedData;

        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    @Transactional
    public void deleteDataById(Long id) {
        if (vehicleTypeRepository.existsById(id)) {
            vehicleTypeClassMapRepository.deleteByVehicleTypeId(id);
            vehicleTypeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleTypeResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleType> records = vehicleTypeRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleTypeResponse> responseData = records.map(record -> {
            List<Long> vehicleClassIds = vehicleTypeClassMapRepository
                    .findVehicleClassIdsByVehicleTypeId(record.getId());
            VehicleTypeResponse response = new VehicleTypeResponse();
            BeanUtils.copyProperties(record, response);
            response.setVehicleClassIds(vehicleClassIds);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleTypeResponse getDataById(Long id) {

        VehicleType existingData = vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleTypeResponse response = new VehicleTypeResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public List<VehicleType> getAllList() {
        return vehicleTypeRepository.findAllByOrderByNameEnAsc();
    }

    public List<?> getActiveList() {
        List<VehicleType> entities = vehicleTypeRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        entities.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("nameBn", item.getNameBn());

            customArray.add(object);
        });

        return customArray;
    }
}
