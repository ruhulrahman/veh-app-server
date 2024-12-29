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

import com.ibas.brta.vehims.configurations.model.ServiceDocumentMap;
import com.ibas.brta.vehims.configurations.model.ServiceEntity;
import com.ibas.brta.vehims.configurations.payload.request.ServiceDocumentMapRequest;
import com.ibas.brta.vehims.configurations.payload.response.DocumentTypeResponse;
import com.ibas.brta.vehims.configurations.payload.response.ServiceDocumentMapResponse;
import com.ibas.brta.vehims.configurations.payload.response.ServiceEntityResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.ServiceDocumentMapRepository;
import com.ibas.brta.vehims.configurations.repository.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceDocumentMapService {

    @Autowired
    ServiceDocumentMapRepository serviceDocumentMapRepository;

    @Autowired
    ServiceEntityService serviceEntityService;

    @Autowired
    DocumentTypeService documentTypeService;

    // Create or Insert operation
    public ServiceDocumentMapResponse createData(ServiceDocumentMapRequest request) {

        ServiceDocumentMap requestObject = new ServiceDocumentMap();
        BeanUtils.copyProperties(request, requestObject);
        ServiceDocumentMap savedData = serviceDocumentMapRepository.save(requestObject);

        ServiceDocumentMapResponse response = new ServiceDocumentMapResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public ServiceDocumentMapResponse updateData(Long id, ServiceDocumentMapRequest request) {

        Optional<ServiceDocumentMap> existingData = serviceDocumentMapRepository.findById(id);

        if (existingData.isPresent()) {
            ServiceDocumentMap requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            ServiceDocumentMap updatedData = serviceDocumentMapRepository.save(requestObject);

            ServiceDocumentMapResponse response = new ServiceDocumentMapResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (serviceDocumentMapRepository.existsById(id)) {
            serviceDocumentMapRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<ServiceDocumentMapResponse> findAllBySearch(Long documentTypeId, Long serviceId, int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<ServiceDocumentMap> records = serviceDocumentMapRepository.findListWithPaginationBySearch(documentTypeId,
                serviceId, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<ServiceDocumentMapResponse> responseData = records.map(record -> {
            ServiceDocumentMapResponse response = new ServiceDocumentMapResponse();
            BeanUtils.copyProperties(record, response);

            DocumentTypeResponse documentTypeResponse = documentTypeService.getDataById(record.getDocumentTypeId());
            response.setDocumentType(documentTypeResponse);

            ServiceEntity serviceEntity = serviceEntityService.getDataById(record.getServiceId());
            ServiceEntityResponse serviceEntityResponse = new ServiceEntityResponse();
            BeanUtils.copyProperties(serviceEntity, serviceEntityResponse);
            response.setService(serviceEntityResponse);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public ServiceDocumentMapResponse getDataById(Long id) {

        ServiceDocumentMap existingData = serviceDocumentMapRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        ServiceDocumentMapResponse response = new ServiceDocumentMapResponse();
        BeanUtils.copyProperties(existingData, response);

        DocumentTypeResponse documentTypeResponse = documentTypeService.getDataById(existingData.getDocumentTypeId());
        response.setDocumentType(documentTypeResponse);

        ServiceEntity serviceEntity = serviceEntityService.getDataById(existingData.getServiceId());
        ServiceEntityResponse serviceEntityResponse = new ServiceEntityResponse();
        BeanUtils.copyProperties(serviceEntity, serviceEntityResponse);
        response.setService(serviceEntityResponse);

        return response;
    }

    public List<ServiceDocumentMapResponse> getServiceDocumentMapByServiceId(Long serviceId) {
        List<ServiceDocumentMap> existingData = serviceDocumentMapRepository
                .findByServiceIdOrderByPriorityAsc(serviceId);
        List<ServiceDocumentMapResponse> responseData = new ArrayList<>();
        if (existingData.isEmpty()) {
            return responseData;
        }

        existingData.forEach(record -> {
            ServiceDocumentMapResponse response = new ServiceDocumentMapResponse();
            BeanUtils.copyProperties(record, response);

            DocumentTypeResponse documentTypeResponse = documentTypeService.getDataById(record.getDocumentTypeId());
            response.setDocumentType(documentTypeResponse);

            // ServiceEntity serviceEntity =
            // serviceEntityService.getDataById(record.getServiceId());
            // ServiceEntityResponse serviceEntityResponse = new ServiceEntityResponse();
            // BeanUtils.copyProperties(serviceEntity, serviceEntityResponse);
            // response.setService(serviceEntityResponse);

            responseData.add(response);
        });

        return responseData;
    }

}
