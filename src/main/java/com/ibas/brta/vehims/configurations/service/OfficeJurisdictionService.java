package com.ibas.brta.vehims.configurations.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.postgresql.util.PGobject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.configurations.model.Location;
import com.ibas.brta.vehims.configurations.model.OfficeJurisdiction;
import com.ibas.brta.vehims.configurations.model.Organization;
import com.ibas.brta.vehims.configurations.payload.request.OfficeJurisdictionRequest;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.OfficeJurisdictionResponse;
import com.ibas.brta.vehims.configurations.payload.response.OrganizationWithThanas;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.LocationRepository;
import com.ibas.brta.vehims.configurations.repository.OfficeJurisdictionRepository;
import com.ibas.brta.vehims.configurations.repository.OrganizationRepository;
import com.ibas.brta.vehims.util.Utils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Autowired
    LocationRepository locationRepository;

    // Create or Insert operation
    @Transactional
    public OfficeJurisdictionResponse createData(OfficeJurisdictionRequest request) {

        officeJurisdictionRepository.deleteByOrgId(request.getOrgId());

        OfficeJurisdictionResponse response = new OfficeJurisdictionResponse();

        for (Long thanaId : request.getThanaIds()) {
            OfficeJurisdiction requestObject = new OfficeJurisdiction();
            BeanUtils.copyProperties(request, requestObject);
            requestObject.setThanaId(thanaId);
            OfficeJurisdiction savedData = officeJurisdictionRepository.save(requestObject);
            BeanUtils.copyProperties(savedData, response);
        }

        return response;
    }

    // Update operation
    @Transactional
    public OfficeJurisdictionResponse updateData(OfficeJurisdictionRequest request) {

        officeJurisdictionRepository.deleteByOrgId(request.getOrgId());

        OfficeJurisdictionResponse response = new OfficeJurisdictionResponse();

        for (Long thanaId : request.getThanaIds()) {
            OfficeJurisdiction requestObject = new OfficeJurisdiction();
            BeanUtils.copyProperties(request, requestObject);
            requestObject.setThanaId(thanaId);
            OfficeJurisdiction savedData = officeJurisdictionRepository.save(requestObject);
            BeanUtils.copyProperties(savedData, response);
        }

        return response;
    }

    // Delete operation
    public void deleteData(Long id) {
        officeJurisdictionRepository.deleteByOrgId(id);
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

    // List all records
    public PagedResponse<OrganizationWithThanas> findListWithPaginationBySearchNative(
            Long orgId, Long thanaId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Object[]> records = officeJurisdictionRepository.findListWithPaginationBySearchNative(
                orgId,
                thanaId,
                isActive,
                pageable);

        // Map raw data to DTOs
        List<OrganizationWithThanas> responseData = records.map(row -> {
            OrganizationWithThanas response = new OrganizationWithThanas();
            BeanUtils.copyProperties(row, response);
            response.setIsEdit(true);
            response.setOrgId(((Number) row[0]).longValue());
            response.setOrgNameEn((String) row[1]);
            response.setOrgNameBn((String) row[2]);
            response.setThanaIds(Arrays.asList((Long[]) row[3]));
            response.setIsActive((Boolean) row[4]);

            Optional<Long> districtId = locationRepository.findFirstParentIdByLocationIds(response.getThanaIds());
            response.setDistrictId(districtId.orElse(null));

            if (districtId.isPresent()) {
                Optional<Location> division = locationRepository.findById(districtId.get());
                if (division.isPresent()) {
                    response.setDivisionId(division.get().getParentId());
                }
            }

            List<LocationResponse> thanas = response.getThanaIds().stream()
                    .map(thanaIdParam -> locationService.getDataById(thanaIdParam)).collect(Collectors.toList());

            response.setThanas(thanas);
            return response;
        }).getContent();

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

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
