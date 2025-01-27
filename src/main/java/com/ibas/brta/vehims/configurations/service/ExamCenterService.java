package com.ibas.brta.vehims.configurations.service;

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

import com.ibas.brta.vehims.configurations.model.ExamCenter;
import com.ibas.brta.vehims.configurations.model.Location;
import com.ibas.brta.vehims.configurations.model.Organization;
import com.ibas.brta.vehims.configurations.payload.request.ExamCenterRequest;
import com.ibas.brta.vehims.configurations.payload.response.ExamCenterResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.ExamCenterRepository;
import com.ibas.brta.vehims.configurations.repository.LocationRepository;
import com.ibas.brta.vehims.configurations.repository.OrganizationRepository;
import com.ibas.brta.vehims.exception.FieldValidationException;

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

    @Autowired
    LocationRepository locationRepository;

    // Create or Insert operation
    public ExamCenterResponse createData(ExamCenterRequest request) {

        ExamCenter requestObject = new ExamCenter();
        BeanUtils.copyProperties(request, requestObject);
        ExamCenter savedData = examCenterRepository.save(requestObject);

        ExamCenterResponse response = new ExamCenterResponse();
        BeanUtils.copyProperties(savedData, response);

        organizationRepository.findById(response.getOrgId()).ifPresent(organization -> {
            response.setOrgNameEn(organization.getNameEn());
            response.setOrgNameBn(organization.getNameBn());
        });
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

            organizationRepository.findById(response.getOrgId()).ifPresent(organization -> {
                response.setOrgNameEn(organization.getNameEn());
                response.setOrgNameBn(organization.getNameBn());
            });

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

            Optional<Location> thana = locationRepository.findById(record.getThanaId());

            if (thana.isPresent()) {

                response.setThanaNameEn(thana.get().getNameEn());
                response.setThanaNameBn(thana.get().getNameBn());
                response.setDistrictId(thana.get().getParentId());

                Optional<Location> division = locationRepository.findById(thana.get().getParentId());
                if (division.isPresent()) {
                    response.setDivisionId(division.get().getParentId());
                }

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

        Optional<Location> thana = locationRepository.findById(response.getThanaId());

        if (thana.isPresent()) {

            response.setThanaNameEn(thana.get().getNameEn());
            response.setThanaNameBn(thana.get().getNameBn());
            response.setDistrictId(thana.get().getParentId());

            Optional<Location> division = locationRepository.findById(thana.get().getParentId());
            if (division.isPresent()) {
                response.setDivisionId(division.get().getParentId());
            }

        }

        return response;
    }

}
