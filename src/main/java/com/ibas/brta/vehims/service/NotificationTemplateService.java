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

import com.ibas.brta.vehims.model.NotificationTemplate;
import com.ibas.brta.vehims.model.ServiceEntity;
import com.ibas.brta.vehims.payload.request.NotificationTemplateRequest;
import com.ibas.brta.vehims.payload.response.NotificationTemplateResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.ServiceEntityResponse;
import com.ibas.brta.vehims.repository.NotificationTemplateRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationTemplateService {
    @Autowired
    NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    ServiceEntityService serviceEntityService;

    // Create or Insert operation
    public NotificationTemplateResponse createData(NotificationTemplateRequest request) {

        NotificationTemplate requestObject = new NotificationTemplate();
        BeanUtils.copyProperties(request, requestObject);
        NotificationTemplate savedData = notificationTemplateRepository.save(requestObject);

        NotificationTemplateResponse response = new NotificationTemplateResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public NotificationTemplateResponse updateData(Long id, NotificationTemplateRequest request) {

        Optional<NotificationTemplate> existingData = notificationTemplateRepository.findById(id);

        if (existingData.isPresent()) {
            NotificationTemplate requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            NotificationTemplate updatedData = notificationTemplateRepository.save(requestObject);

            NotificationTemplateResponse response = new NotificationTemplateResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (notificationTemplateRepository.existsById(id)) {
            notificationTemplateRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<NotificationTemplateResponse> findAllBySearch(String nameEn, Long serviceId,
            Boolean isActive, int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<NotificationTemplate> records = notificationTemplateRepository.findListWithPaginationBySearch(nameEn,
                serviceId,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<NotificationTemplateResponse> responseData = records.map(record -> {
            NotificationTemplateResponse response = new NotificationTemplateResponse();
            BeanUtils.copyProperties(record, response);

            if (record.getServiceId() != null) {
                ServiceEntity serviceEntity = serviceEntityService.getDataById(record.getServiceId());
                if (serviceEntity != null) {
                    ServiceEntityResponse serviceEntityResponse = new ServiceEntityResponse();
                    BeanUtils.copyProperties(serviceEntity, serviceEntityResponse);
                    response.setService(serviceEntityResponse);
                } else {
                    // Handle the case when Service is not found
                    log.error("Service with id {} not found", record.getServiceId());
                }
            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public NotificationTemplateResponse getDataById(Long id) {

        NotificationTemplate existingData = notificationTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        NotificationTemplateResponse response = new NotificationTemplateResponse();
        BeanUtils.copyProperties(existingData, response);

        if (existingData.getServiceId() != null) {
            ServiceEntity serviceEntity = serviceEntityService.getDataById(existingData.getServiceId());
            if (serviceEntity != null) {
                ServiceEntityResponse serviceEntityResponse = new ServiceEntityResponse();
                BeanUtils.copyProperties(serviceEntity, serviceEntityResponse);
                response.setService(serviceEntityResponse);
            } else {
                // Handle the case when Service is not found
                log.error("Service with id {} not found", existingData.getServiceId());
            }
        }
        return response;
    }

    public List<?> getActiveList() {
        List<NotificationTemplate> listData = notificationTemplateRepository.findByIsActiveTrueOrderByTitleEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("titleEn", serviceEntity.getTitleEn());
            object.put("titleBn", serviceEntity.getTitleBn());
            object.put("serviceId", serviceEntity.getServiceId());
            customArray.add(object);
        });

        return customArray;
    }
}
