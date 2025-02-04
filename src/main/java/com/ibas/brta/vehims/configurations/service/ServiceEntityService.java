package com.ibas.brta.vehims.configurations.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.configurations.model.ServiceEntity;
import com.ibas.brta.vehims.configurations.payload.request.ServiceEntityDTO;
import com.ibas.brta.vehims.common.model.ServiceEconomicCode;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.common.payload.response.ServiceEconomicCodeResponse;
import com.ibas.brta.vehims.common.repository.ServiceEconomicCodeRepository;
import com.ibas.brta.vehims.configurations.payload.response.ServiceEntityResponse;
import com.ibas.brta.vehims.configurations.repository.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;

@Slf4j
@Service
public class ServiceEntityService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceEconomicCodeRepository serviceEconomicCodeRepository;

    // Create or Insert operation
    public ServiceEntityDTO createData(ServiceEntityDTO serviceDTO) {
        ServiceEntity serviceEntity = new ServiceEntity();

        BeanUtils.copyProperties(serviceDTO, serviceEntity, "id", "parentService");

        ServiceEntity saveData = serviceRepository.save(serviceEntity);

        ServiceEntityDTO responseData = new ServiceEntityDTO();
        BeanUtils.copyProperties(saveData, responseData);

        return responseData;
    }

    // Update operation
    public ServiceEntityDTO updateData(Long id, ServiceEntityDTO serviceDTO) {

        Optional<ServiceEntity> existingData = serviceRepository.findById(id);

        if (existingData.isPresent()) {
            ServiceEntity serviceEntity = existingData.get();
            BeanUtils.copyProperties(serviceDTO, serviceEntity, "id", "parentService"); // Exclude ID
            // Set parent service if parentServiceId is provided

            ServiceEntity saveData = serviceRepository.save(serviceEntity);

            ServiceEntityDTO responseData = new ServiceEntityDTO();
            BeanUtils.copyProperties(saveData, responseData);

            return responseData;

        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<ServiceEntityResponse> findAllBySearch(String nameEn, Long parentServiceId, Boolean isActive,
            int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<ServiceEntity> records = serviceRepository.findListWithPaginationBySearch(nameEn, parentServiceId,
                isActive, pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<ServiceEntityResponse> responseData = records.map(record -> {
            ServiceEntityResponse response = new ServiceEntityResponse();
            BeanUtils.copyProperties(record, response);

            List<ServiceEntityResponse> childServices = getServicesByParentServiceCode(record.getServiceCode());

            response.setChildServices(childServices);

            if (record.getParentServiceId() != null) {

                Optional<ServiceEntity> parentService = serviceRepository.findById(record.getParentServiceId());
                if (parentService.isPresent()) {
                    ServiceEntityResponse parentServiceResponse = new ServiceEntityResponse();
                    BeanUtils.copyProperties(parentService.get(), parentServiceResponse);
                    response.setParentService(parentServiceResponse);
                }
            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    public List<ServiceEntityResponse> getServicesByParentServiceCode(String serviceCode) {
        // Get childServiceIds for the given serviceCode
        List<Long> childServiceIds = serviceRepository.findChildServiceIdsByServiceCode(serviceCode);

        // If there are childServiceIds, fetch the corresponding ServiceEntity
        if (childServiceIds != null && !childServiceIds.isEmpty()) {
            return getServicesByIds(childServiceIds);
        }

        // Return an empty list if no childServiceIds are found
        return List.of();
    }

    public List<ServiceEntityResponse> getServicesByIds(List<Long> ids) {

        List<ServiceEntity> records = serviceRepository.findByIdInOrderByPriorityAsc(ids);
        if (records.size() == 0) {
            return List.of();
        }

        List<ServiceEntityResponse> responseData = records.stream().map(record -> {
            ServiceEntityResponse response = new ServiceEntityResponse();
            BeanUtils.copyProperties(record, response);

            return response;
        }).collect(Collectors.toList());

        return responseData;
    }

    public List<ServiceEntityResponse> getActiveServicesByParentServiceCode(String serviceCode) {
        // Get childServiceIds for the given serviceCode
        List<Long> childServiceIds = serviceRepository.findChildServiceIdsByServiceCode(serviceCode);

        // If there are childServiceIds, fetch the corresponding ServiceEntity
        if (childServiceIds != null && !childServiceIds.isEmpty()) {
            return getActiveServicesByIds(childServiceIds);
        }

        // Return an empty list if no childServiceIds are found
        return List.of();
    }

    public List<ServiceEntityResponse> getActiveServicesByIds(List<Long> ids) {

        List<ServiceEntity> records = serviceRepository.findByIdInAndIsActiveTrueOrderByPriorityAsc(ids);
        if (records.size() == 0) {
            return List.of();
        }

        List<ServiceEntityResponse> responseData = records.stream().map(record -> {
            ServiceEntityResponse response = new ServiceEntityResponse();
            BeanUtils.copyProperties(record, response);

            return response;
        }).collect(Collectors.toList());

        return responseData;
    }

    // Find a single record by ID
    public ServiceEntity getDataById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));
    }

    public List<ServiceEntity> findAllByOrderByNameEnAsc() {
        List<ServiceEntity> responseData = serviceRepository.findAllByOrderByNameEnAsc();
        return responseData;
    }

    // Get all services with parentService object
    public List<?> getAllList() {
        List<ServiceEntity> serviceEntities = serviceRepository.findAllByOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        serviceEntities.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("nameEn", serviceEntity.getNameEn());
            object.put("nameBn", serviceEntity.getNameBn());
            object.put("isActive", serviceEntity.getIsActive());
            object.put("parentServiceId", serviceEntity.getParentServiceId());

            customArray.add(object);
        });

        return customArray;

        // return serviceEntities.stream().map(
        // serviceEntity -> {
        // ServiceEntityResponse serviceDTO = new ServiceEntityResponse();
        // BeanUtils.copyProperties(serviceEntity, serviceDTO, "isActive",
        // "serviceCode", "parentServiceId",
        // "parentService");

        // return serviceDTO;
        // }).collect(Collectors.toList());
    }

    public List<Long> getServicesIds() {
        return serviceRepository.getServicesIdsIsActiveTrue();
    }

    public List<?> getChildServicesByParentServiceCode(String parentServiceCode) {

        List<ServiceEntity> serviceEntities = serviceRepository.getChildServicesByParentServiceCode(parentServiceCode);

        List<Map<String, Object>> customArray = new ArrayList<>();

        serviceEntities.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("nameEn", serviceEntity.getNameEn());
            object.put("nameBn", serviceEntity.getNameBn());

            customArray.add(object);
        });

        return customArray;
    }

    public List<?> getChildServicesWithAdditionalByParentServiceCode(String parentServiceCode) {

        List<ServiceEntity> serviceEntities = serviceRepository.getChildServicesByParentServiceCode(parentServiceCode);
        List<ServiceEntity> commonFeesServices = serviceRepository
                .getServicesByServiceCodes(Arrays.asList("postal_service_fee", "card_fee", "application_fee"));

        List<Map<String, Object>> customArray = new ArrayList<>();

        serviceEntities.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("nameEn", serviceEntity.getNameEn());
            object.put("nameBn", serviceEntity.getNameBn());

            customArray.add(object);
        });

        commonFeesServices.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("nameEn", serviceEntity.getNameEn());
            object.put("nameBn", serviceEntity.getNameBn());

            customArray.add(object);
        });

        return customArray;
    }

    // Helper method to convert Entity to DTO with parentService object
    private ServiceEntityResponse convertToDTO(ServiceEntity serviceEntity) {
        ServiceEntityResponse serviceDTO = new ServiceEntityResponse();
        BeanUtils.copyProperties(serviceEntity, serviceDTO);
        return serviceDTO;
    }

    public List<?> getActiveList() {
        List<ServiceEntity> serviceEntities = serviceRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        serviceEntities.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("nameEn", serviceEntity.getNameEn());
            object.put("nameBn", serviceEntity.getNameBn());

            customArray.add(object);
        });

        return customArray;
    }

    public List<?> getServiceEconomicCodeList() {
        List<ServiceEconomicCode> serviceEconomicCodes = serviceEconomicCodeRepository.findAll();
        List<ServiceEconomicCodeResponse> serviceEconomicCodeResponseList = new ArrayList<>();

        serviceEconomicCodes.forEach(serviceEconomicCode -> {
            ServiceEconomicCodeResponse serviceEconomicCodeResponse = new ServiceEconomicCodeResponse();
            BeanUtils.copyProperties(serviceEconomicCode, serviceEconomicCodeResponse);
            serviceEconomicCodeResponseList.add(serviceEconomicCodeResponse);
        });

        return serviceEconomicCodeResponseList;
    }

}
