package com.ibas.brta.vehims.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.configurations.EmailTemplate;
import com.ibas.brta.vehims.model.configurations.ServiceEntity;
import com.ibas.brta.vehims.payload.request.EmailTemplateRequest;
import com.ibas.brta.vehims.payload.response.EmailTemplateResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.ServiceEntityResponse;
import com.ibas.brta.vehims.repository.EmailTemplateRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailTemplateService {
    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    ServiceEntityService serviceEntityService;

    // Create or Insert operation
    public EmailTemplateResponse createData(EmailTemplateRequest request) {

        EmailTemplate requestObject = new EmailTemplate();
        BeanUtils.copyProperties(request, requestObject);
        EmailTemplate savedData = emailTemplateRepository.save(requestObject);

        EmailTemplateResponse response = new EmailTemplateResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public EmailTemplateResponse updateData(Long id, EmailTemplateRequest request) {

        Optional<EmailTemplate> existingData = emailTemplateRepository.findById(id);

        if (existingData.isPresent()) {
            EmailTemplate requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            EmailTemplate updatedData = emailTemplateRepository.save(requestObject);

            EmailTemplateResponse response = new EmailTemplateResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (emailTemplateRepository.existsById(id)) {
            emailTemplateRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<EmailTemplateResponse> findAllBySearch(String templateName, Long serviceId,
            Boolean isActive, int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<EmailTemplate> records = emailTemplateRepository.findListWithPaginationBySearch(templateName,
                serviceId,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<EmailTemplateResponse> responseData = records.map(record -> {
            EmailTemplateResponse response = new EmailTemplateResponse();
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
    public EmailTemplateResponse getDataById(Long id) {

        EmailTemplate existingData = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        EmailTemplateResponse response = new EmailTemplateResponse();
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
}
