package com.ibas.brta.vehims.configurations.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.configurations.model.BloodGroup;
import com.ibas.brta.vehims.configurations.payload.request.BloodGroupDTO;
import com.ibas.brta.vehims.configurations.payload.response.BloodGroupResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.BloodGroupRepository;
import com.ibas.brta.vehims.exception.FieldValidationException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BloodGroupService {
    @Autowired
    private BloodGroupRepository bloodGroupRepository;

    // Create or Insert operation
    public BloodGroup createData(BloodGroupDTO request) {

        if (bloodGroupRepository.existsByNameEn(request.getNameEn())) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Blood Group with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }
        BloodGroup bloodGroup = new BloodGroup();
        BeanUtils.copyProperties(request, bloodGroup);
        return bloodGroupRepository.save(bloodGroup);
    }

    // Update operation
    public BloodGroup updateData(Long id, BloodGroupDTO request) {

        if (bloodGroupRepository.existsByNameEnAndIdNot(request.getNameEn(), id)) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Blood Group with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }

        Optional<BloodGroup> existingData = bloodGroupRepository.findById(id);
        if (existingData.isPresent()) {
            BloodGroup bloodGroup = existingData.get();
            BeanUtils.copyProperties(request, bloodGroup);
            return bloodGroupRepository.save(bloodGroup);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteDataById(Long id) {
        if (bloodGroupRepository.existsById(id)) {
            bloodGroupRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<BloodGroupResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<BloodGroup> records = bloodGroupRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<BloodGroupResponse> responseData = records.map(record -> {
            // return ModelMapper.VehicleTypeToResponse(record);
            BloodGroupResponse response = new BloodGroupResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public BloodGroupResponse getDataById(Long id) {
        BloodGroup bloodGroup = bloodGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        BloodGroupResponse response = new BloodGroupResponse();
        BeanUtils.copyProperties(bloodGroup, response);
        return response;
    }

    public List<BloodGroupResponse> getAllList() {
        List<BloodGroup> records = bloodGroupRepository.findAllByOrderByNameEnAsc();
        // Map Responses with all information
        List<BloodGroupResponse> responseData = records.stream().map(record -> {
            // return ModelMapper.VehicleTypeToResponse(record);
            BloodGroupResponse response = new BloodGroupResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).collect(Collectors.toList());

        return responseData;
    }

    public List<?> getActiveList() {
        List<BloodGroup> entities = bloodGroupRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        entities.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("nameEn", serviceEntity.getNameEn());
            object.put("nameBn", serviceEntity.getNameBn());

            customArray.add(object);
        });

        return customArray;
    }
}
