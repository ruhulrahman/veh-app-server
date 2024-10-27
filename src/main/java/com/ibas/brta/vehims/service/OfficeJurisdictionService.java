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

import com.ibas.brta.vehims.model.configurations.OfficeJurisdiction;
import com.ibas.brta.vehims.model.configurations.Organization;
import com.ibas.brta.vehims.payload.request.OfficeJurisdictionRequest;
import com.ibas.brta.vehims.payload.response.LocationResponse;
import com.ibas.brta.vehims.payload.response.OfficeJurisdictionResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.OfficeJurisdictionRepository;
import com.ibas.brta.vehims.repository.OrganizationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OfficeJurisdictionService {

    @Autowired
    OfficeJurisdictionRepository officeJurisdictionRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    LocationService locationService;

    // Create or Insert operation
    public OfficeJurisdictionResponse createData(OfficeJurisdictionRequest request) {

        OfficeJurisdiction requestObject = new OfficeJurisdiction();
        BeanUtils.copyProperties(request, requestObject);
        OfficeJurisdiction savedData = officeJurisdictionRepository.save(requestObject);

        OfficeJurisdictionResponse response = new OfficeJurisdictionResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public OfficeJurisdictionResponse updateData(Long id, OfficeJurisdictionRequest request) {

        Optional<OfficeJurisdiction> existingData = officeJurisdictionRepository.findById(id);

        if (existingData.isPresent()) {
            OfficeJurisdiction requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            OfficeJurisdiction updatedData = officeJurisdictionRepository.save(requestObject);

            OfficeJurisdictionResponse response = new OfficeJurisdictionResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (officeJurisdictionRepository.existsById(id)) {
            officeJurisdictionRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<OfficeJurisdictionResponse> findAllBySearch(
            Long orgId, Long thanaId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<OfficeJurisdiction> records = officeJurisdictionRepository.findListWithPaginationBySearch(
                orgId,
                thanaId,
                isActive,
                pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<OfficeJurisdictionResponse> responseData = records.map(record -> {
            OfficeJurisdictionResponse response = new OfficeJurisdictionResponse();
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
    public OfficeJurisdictionResponse getDataById(Long id) {

        OfficeJurisdiction existingData = officeJurisdictionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        OfficeJurisdictionResponse response = new OfficeJurisdictionResponse();
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
