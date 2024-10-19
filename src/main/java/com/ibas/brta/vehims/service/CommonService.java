package com.ibas.brta.vehims.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.payload.response.StatusGroupResponse;
import com.ibas.brta.vehims.payload.response.UserOfficeRoleResponse;
import com.ibas.brta.vehims.projection.CommonProjection;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.repository.CommonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonService {

    @Autowired
    CommonRepository commonRepository;

    public StatusGroupResponse findByStatusGroupByCode(String statusGroupCode) {

        StatusGroup statusGroup = commonRepository.findByStatusGroupByCode(statusGroupCode);

        StatusGroupResponse response = new StatusGroupResponse();
        BeanUtils.copyProperties(statusGroup, response);
        return response;
    }

    public List<?> findByStatusesByGroupCode(String statusGroupCode) {
        return commonRepository.findByStatusesByGroupCode(statusGroupCode);
    }

    public List<?> getActiveOrganizations() {
        List<CommonProjection> listData = commonRepository.getActiveOrganizations();

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

    public StatusProjection getStatusByStatusCodeOrId(String statusCodeOrId) {
        StatusProjection statusData = commonRepository.getStatusByStatusCodeOrId(statusCodeOrId);

        return statusData;
    }

    public List<?> getActiveLocationsByLocationTypeId(Long locationTypeId) {
        List<CommonProjection> listData = commonRepository.getActiveLocationsByLocationTypeId(locationTypeId);

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

    public List<Integer> getRoleIdsByUserId(Long userId) {
        return commonRepository.getRoleIdsByUserId(userId);
    }

    public List<Integer> getPermissionIdsByRoleIds(List<Integer> roleIds) {
        return commonRepository.getPermissionIdsByRoleIds(roleIds);
    }

    public List<Integer> getPermissionIdsByRoleId(Integer roleId) {
        return commonRepository.getPermissionIdsByRoleId(roleId);
    }

    public List<String> getPermissionCodeByPermissionIds(List<Integer> permissionIds) {
        return commonRepository.getPermissionCodeByPermissionIds(permissionIds);
    }

    public List<UserOfficeRoleResponse> getUserOfficeRolesByUserId(Long userId) {
        List<UserOfficeRoleResponse> userOfficeRoles = commonRepository.getUserOfficeRolesByUserId(userId);
        log.info("userOfficeRoles in service ====== {}" + userOfficeRoles);
        return userOfficeRoles;
    }

    public List<?> getActiveExporters() {
        List<CommonProjection> listData = commonRepository.getActiveExporters();

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

    public List<?> getActiveImporters() {
        List<CommonProjection> listData = commonRepository.getActiveImporters();

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
