package com.ibas.brta.vehims.service;

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

import com.ibas.brta.vehims.model.configurations.VehicleClass;
import com.ibas.brta.vehims.payload.request.VehicleClassRequest;
import com.ibas.brta.vehims.payload.response.VehicleClassResponse;
import com.ibas.brta.vehims.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.VehicleClassRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleClassService {

    @Autowired
    VehicleClassRepository vehicleClassRepository;

    @Autowired
    VehicleTypeService vehicleTypeService;

    // Create or Insert operation
    public VehicleClassResponse createData(VehicleClassRequest request) {

        VehicleClass requestObject = new VehicleClass();
        BeanUtils.copyProperties(request, requestObject);
        VehicleClass savedData = vehicleClassRepository.save(requestObject);

        VehicleClassResponse response = new VehicleClassResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleClassResponse updateData(Long id, VehicleClassRequest request) {

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
}
