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

import com.ibas.brta.vehims.configurations.model.VehicleClass;
import com.ibas.brta.vehims.configurations.payload.request.VehicleClassRequest;
import com.ibas.brta.vehims.configurations.payload.response.VehicleClassResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.VehicleClassRepository;
import com.ibas.brta.vehims.configurations.repository.VehicleTypeClassMapRepository;
import com.ibas.brta.vehims.exception.FieldValidationException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleClassService {

    @Autowired
    VehicleClassRepository vehicleClassRepository;

    @Autowired
    VehicleTypeService vehicleTypeService;

    @Autowired
    private VehicleTypeClassMapRepository vehicleTypeClassMapRepository;

    // Create or Insert operation
    public VehicleClassResponse createData(VehicleClassRequest request) {

        if (vehicleClassRepository.existsByNameEn(request.getNameEn())) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Vehicle Class with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }

        VehicleClass requestObject = new VehicleClass();
        BeanUtils.copyProperties(request, requestObject);
        VehicleClass savedData = vehicleClassRepository.save(requestObject);

        VehicleClassResponse response = new VehicleClassResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleClassResponse updateData(Long id, VehicleClassRequest request) {

        if (vehicleClassRepository.existsByNameEnAndIdNot(request.getNameEn(), id)) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Vehicle Class with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }

        Optional<VehicleClass> existingData = vehicleClassRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleClass requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleClass updatedData = vehicleClassRepository.save(requestObject);

            VehicleClassResponse response = new VehicleClassResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (vehicleClassRepository.existsById(id)) {
            vehicleClassRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleClassResponse> findAllBySearch(String nameEn, Long vehicleTypeId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleClass> records = vehicleClassRepository.findListWithPaginationBySearch(nameEn,
                vehicleTypeId,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleClassResponse> responseData = records.map(record -> {
            VehicleClassResponse response = new VehicleClassResponse();
            BeanUtils.copyProperties(record, response);

            VehicleTypeResponse statusResponse = vehicleTypeService.getDataById(record.getVehicleTypeId());
            response.setVehicleType(statusResponse);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleClassResponse getDataById(Long id) {

        VehicleClass existingData = vehicleClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleClassResponse response = new VehicleClassResponse();
        BeanUtils.copyProperties(existingData, response);

        VehicleTypeResponse statusResponse = vehicleTypeService.getDataById(existingData.getVehicleTypeId());
        response.setVehicleType(statusResponse);

        return response;
    }

    public List<?> getActiveList() {
        List<VehicleClass> listData = vehicleClassRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("nameBn", item.getNameBn());

            customArray.add(object);
        });

        return customArray;
    }

    public List<VehicleClassResponse> getVehicleClasses(Long vehicleTypeId) {

        List<Long> vehicleClassIds = vehicleTypeClassMapRepository.findVehicleClassIdsByVehicleTypeId(vehicleTypeId);
        List<VehicleClass> vehicleClasses = vehicleClassRepository
                .findByIdsIsActiveTrueOrderByNameEnAsc(vehicleClassIds);

        List<VehicleClassResponse> response = new ArrayList<VehicleClassResponse>();
        for (VehicleClass vehicleClass : vehicleClasses) {
            VehicleClassResponse vehicleClassResponse = new VehicleClassResponse();
            BeanUtils.copyProperties(vehicleClass, vehicleClassResponse);
            response.add(vehicleClassResponse);
        }

        return response;
    }
}
