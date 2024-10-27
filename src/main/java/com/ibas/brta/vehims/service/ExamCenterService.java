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

import com.ibas.brta.vehims.model.configurations.ExamCenter;
import com.ibas.brta.vehims.model.configurations.Organization;
import com.ibas.brta.vehims.payload.request.ExamCenterRequest;
import com.ibas.brta.vehims.payload.response.ExamCenterResponse;
import com.ibas.brta.vehims.payload.response.LocationResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.ExamCenterRepository;
import com.ibas.brta.vehims.repository.OrganizationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExamCenterService {

    @Autowired
    ExamCenterRepository examCenterRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    LocationService locationService;

    // Create or Insert operation
    public ExamCenterResponse createData(ExamCenterRequest request) {

        ExamCenter requestObject = new ExamCenter();
        BeanUtils.copyProperties(request, requestObject);
        ExamCenter savedData = examCenterRepository.save(requestObject);

        ExamCenterResponse response = new ExamCenterResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public ExamCenterResponse updateData(Long id, ExamCenterRequest request) {

        Optional<ExamCenter> existingData = examCenterRepository.findById(id);

        if (existingData.isPresent()) {
            ExamCenter requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            ExamCenter updatedData = examCenterRepository.save(requestObject);

            ExamCenterResponse response = new ExamCenterResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (examCenterRepository.existsById(id)) {
            examCenterRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<ExamCenterResponse> findAllBySearch(
            Long orgId, Long thanaId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<ExamCenter> records = examCenterRepository.findListWithPaginationBySearch(
                orgId,
                thanaId,
                isActive,
                pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<ExamCenterResponse> responseData = records.map(record -> {
            ExamCenterResponse response = new ExamCenterResponse();
            BeanUtils.copyProperties(record, response);

            Optional<Organization> organization = organizationRepository.findById(record.getOrgId());
            if (organization.isPresent()) {
                response.setOrgNameEn(organization.get().getNameEn());
                response.setOrgNameBn(organization.get().getNameBn());
            }

            LocationResponse thana = locationService.getDataById(record.getThanaId());
            if (thana != null) {
                response.setThanaNameEn(thana.getNameEn());
                response.setThanaNameBn(thana.getNameBn());
            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public ExamCenterResponse getDataById(Long id) {

        ExamCenter existingData = examCenterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        ExamCenterResponse response = new ExamCenterResponse();
        BeanUtils.copyProperties(existingData, response);

        Optional<Organization> organization = organizationRepository.findById(response.getOrgId());
        if (organization.isPresent()) {
            response.setOrgNameEn(organization.get().getNameEn());
            response.setOrgNameBn(organization.get().getNameBn());
        }

        LocationResponse thana = locationService.getDataById(response.getThanaId());
        if (thana != null) {
            response.setThanaNameEn(thana.getNameEn());
            response.setThanaNameBn(thana.getNameBn());
        }
        
        return response;
    }

}
