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

import com.ibas.brta.vehims.configurations.model.Organization;
import com.ibas.brta.vehims.configurations.payload.request.OrganizationRequest;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.OrganizationResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import com.ibas.brta.vehims.configurations.repository.OrganizationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    LocationService locationService;

    // Create or Insert operation
    public OrganizationResponse createData(OrganizationRequest request) {

        Organization requestObject = new Organization();
        BeanUtils.copyProperties(request, requestObject);
        Organization savedData = organizationRepository.save(requestObject);

        OrganizationResponse response = new OrganizationResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public OrganizationResponse updateData(Long id, OrganizationRequest request) {

        Optional<Organization> existingData = organizationRepository.findById(id);

        if (existingData.isPresent()) {
            Organization requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            Organization updatedData = organizationRepository.save(requestObject);

            OrganizationResponse response = new OrganizationResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<OrganizationResponse> findAllBySearch(
            String nameEn, Long officeTypeId, Long divisionId, Long districtId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Organization> records = organizationRepository.findListWithPaginationBySearchWithNativeQuery(
                nameEn,
                officeTypeId,
                divisionId,
                districtId,
                isActive,
                pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<OrganizationResponse> responseData = records.map(record -> {
            OrganizationResponse response = new OrganizationResponse();
            BeanUtils.copyProperties(record, response);

            StatusResponse statusResponse = statusService.findStatusById(record.getOfficeTypeId());

            if (statusResponse != null) {
                response.setOfficeTypeNameEn(statusResponse.getNameEn());
                response.setOfficeTypeNameBn(statusResponse.getNameBn());
            }

            LocationResponse locationResponse = locationService.getDataById(response.getLocationId());
            if (locationResponse != null) {
                if (locationResponse.getParentId() != null) {

                    String locationEn = locationResponse.getNameEn();
                    String locationBn = locationResponse.getNameBn();

                    LocationResponse district = locationService.getDataById(locationResponse.getParentId());
                    if (district != null) {
                        if (district.getParentId() != null) {

                            locationEn = locationResponse.getNameEn() + district.getNameEn();
                            locationBn = locationResponse.getNameBn() + district.getNameBn();

                            LocationResponse division = locationService.getDataById(district.getParentId());
                            if (division != null) {
                                locationEn = locationResponse.getNameEn() + district.getNameEn() + ", "
                                        + division.getNameEn();
                                locationBn = locationResponse.getNameBn() + district.getNameBn() + ", "
                                        + division.getNameBn();
                            }
                        }
                    }

                    response.setLocationEn(locationEn);
                    response.setLocationEn(locationBn);
                }
            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public OrganizationResponse getDataById(Long id) {

        Organization existingData = organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        OrganizationResponse response = new OrganizationResponse();
        BeanUtils.copyProperties(existingData, response);

        StatusResponse statusResponse = statusService.findStatusById(existingData.getOfficeTypeId());
        if (statusResponse != null) {
            response.setOfficeTypeNameEn(statusResponse.getNameEn());
            response.setOfficeTypeNameBn(statusResponse.getNameBn());
        }

        LocationResponse locationResponse = locationService.getDataById(response.getLocationId());
        if (locationResponse != null) {
            if (locationResponse.getParentId() != null) {

                String locationEn = locationResponse.getNameEn();
                String locationBn = locationResponse.getNameBn();

                LocationResponse district = locationService.getDataById(locationResponse.getParentId());
                if (district != null) {
                    if (district.getParentId() != null) {

                        locationEn = locationResponse.getNameEn() + district.getNameEn();
                        locationBn = locationResponse.getNameBn() + district.getNameBn();

                        LocationResponse division = locationService.getDataById(district.getParentId());
                        if (division != null) {
                            locationEn = locationResponse.getNameEn() + district.getNameEn() + ", "
                                    + division.getNameEn();
                            locationBn = locationResponse.getNameBn() + district.getNameBn() + ", "
                                    + division.getNameBn();
                        }
                    }
                }

                response.setLocationEn(locationEn);
                response.setLocationEn(locationBn);
            }
        }

        return response;
    }

    public List<?> getActiveList() {
        List<Organization> listData = organizationRepository.findByIsActiveTrueOrderByNameEnAsc();

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
