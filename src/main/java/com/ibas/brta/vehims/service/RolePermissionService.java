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

import com.ibas.brta.vehims.model.userManagement.RolePermission;
import com.ibas.brta.vehims.model.userManagement.RoleU;
import com.ibas.brta.vehims.payload.request.RolePermissionDTO;
import com.ibas.brta.vehims.payload.request.RoleURequest;
import com.ibas.brta.vehims.payload.response.RoleUResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.RolePermissionRepository;
import com.ibas.brta.vehims.repository.RoleURepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RolePermissionService {

    @Autowired
    RoleURepository roleRepository;

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    // Create or Insert operation
    @Transactional
    public RoleUResponse createData(RoleURequest request) {

        RoleU requestObject = new RoleU();
        BeanUtils.copyProperties(request, requestObject);
        RoleU savedData = roleRepository.save(requestObject);

        if (request.getPermissionIds() != null) {

            for (Long permissionId : request.getPermissionIds()) {

                RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
                rolePermissionDTO.setRoleId(savedData.getId());
                rolePermissionDTO.setPermissionId(permissionId);

                RolePermission requestRolePermission = new RolePermission();
                BeanUtils.copyProperties(rolePermissionDTO, requestRolePermission);

                rolePermissionRepository.save(requestRolePermission);
            }
        }

        RoleUResponse response = new RoleUResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public RoleUResponse updateData(Long id, RoleURequest request) {

        Optional<RoleU> existingData = roleRepository.findById(id);

        if (existingData.isPresent()) {
            RoleU requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            RoleU updatedData = roleRepository.save(requestObject);

            if (request.getPermissionIds() != null) {

                for (Long permissionId : request.getPermissionIds()) {

                    RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
                    rolePermissionDTO.setRoleId(updatedData.getId());
                    rolePermissionDTO.setPermissionId(permissionId);

                    RolePermission requestRolePermission = new RolePermission();
                    BeanUtils.copyProperties(rolePermissionDTO, requestRolePermission);

                    rolePermissionRepository.save(requestRolePermission);
                }
            }

            RoleUResponse response = new RoleUResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<RoleUResponse> findAllBySearch(String nameEn, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<RoleU> records = roleRepository.findListWithPaginationBySearch(nameEn,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<RoleUResponse> responseData = records.map(record -> {
            RoleUResponse response = new RoleUResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public RoleUResponse getDataById(Long id) {

        RoleU existingData = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        RoleUResponse response = new RoleUResponse();
        BeanUtils.copyProperties(existingData, response);

        // response.setTypeName(existingData.getType() == 1 ? "Page" : "Feature");

        // if (response.getParentId() != null) {
        // Optional<RoleU> parent = roleRepository.findById(response.getParentId());
        // if (parent.isPresent()) {
        // response.setParentName(parent.get().getNameEn());
        // }
        // }

        return response;
    }

    public List<?> getActiveList() {
        List<RoleU> listData = roleRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("nameBn", item.getNameBn());
            object.put("roleCode", item.getRoleCode());

            customArray.add(object);
        });

        return customArray;
    }

}
