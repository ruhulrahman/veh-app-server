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

import com.ibas.brta.vehims.model.Permission;
import com.ibas.brta.vehims.payload.request.PermissionRequest;
import com.ibas.brta.vehims.payload.response.PermissionResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.PermissionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    CountryService countryService;

    // Create or Insert operation
    public PermissionResponse createData(PermissionRequest request) {

        Permission requestObject = new Permission();
        BeanUtils.copyProperties(request, requestObject);
        Permission savedData = permissionRepository.save(requestObject);

        PermissionResponse response = new PermissionResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public PermissionResponse updateData(Long id, PermissionRequest request) {

        Optional<Permission> existingData = permissionRepository.findById(id);

        if (existingData.isPresent()) {
            Permission requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            Permission updatedData = permissionRepository.save(requestObject);

            PermissionResponse response = new PermissionResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<PermissionResponse> findAllBySearch(String nameEn, Long type, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Permission> records = permissionRepository.findListWithPaginationBySearch(nameEn,
                type,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<PermissionResponse> responseData = records.map(record -> {
            PermissionResponse response = new PermissionResponse();
            BeanUtils.copyProperties(record, response);

            response.setTypeName(record.getType() == 1 ? "Page" : "Feature");

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public PermissionResponse getDataById(Long id) {

        Permission existingData = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        PermissionResponse response = new PermissionResponse();
        BeanUtils.copyProperties(existingData, response);

        response.setTypeName(existingData.getType() == 1 ? "Page" : "Feature");

        return response;
    }

    public List<?> getActiveList() {
        List<Permission> listData = permissionRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("permissionCode", item.getPermissionCode());
            object.put("type", item.getType());
            object.put("typeName", item.getType() == 1 ? "Page" : "Feature");

            customArray.add(object);
        });

        return customArray;
    }
}
