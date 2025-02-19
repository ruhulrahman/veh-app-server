package com.ibas.brta.vehims.configurations.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ibas.brta.vehims.configurations.model.Location;
import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.configurations.repository.LocationRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
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

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    StatusRepository statusRepository;

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
            String nameEn, Long officeTypeId, Long divisionId, Long districtId, Long thanaId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        log.info("divisionId -> "+ divisionId);
        log.info("districtId -> "+ districtId);
        log.info("thanaId -> "+ thanaId);
        // Retrieve all records from the database
        Page<Organization> records = organizationRepository.findListWithPaginationBySearchWithNativeQuery(
                nameEn,
                officeTypeId,
                divisionId,
                districtId,
                thanaId,
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

            if (record.getLocationId() != null) {

                LocationResponse thanaResponse = getLocationById(record.getLocationId());
//                response.setLocation(thanaResponse);

                response.setLocationEn(thanaResponse.getNameEn());
                response.setLocationBn(thanaResponse.getNameBn());

                LocationResponse districtResponse = getLocationById(thanaResponse.getParentId());
                if (districtResponse != null) {
                    response.setDistrictId(districtResponse.getId());
                    response.setDistrictNameEn(districtResponse.getNameEn());
                    response.setDistrictNameBn(districtResponse.getNameBn());

                    LocationResponse divisionResponse = getLocationById(districtResponse.getParentId());
                    if (divisionResponse != null) {
                        response.setDivisionId(divisionResponse.getId());
                        response.setDivisionNameEn(divisionResponse.getNameEn());
                        response.setDivisionNameBn(divisionResponse.getNameBn());

                        response.setFullAddressEn(
                                response.getAddressEn() + ", "
                                        + thanaResponse.getNameEn() + "-" + response.getPostCode() + ", " + districtResponse.getNameEn() + ", "
                                        + divisionResponse.getNameEn());

                        response.setFullAddressBn(
                                response.getAddressBn() + ", "
                                        + thanaResponse.getNameBn() + "-" + response.getPostCode() + ", " + districtResponse.getNameBn() + ", "
                                        + divisionResponse.getNameBn());
                    }
                }

            }

//            LocationResponse locationResponse = locationService.getDataById(response.getLocationId());
//            if (locationResponse != null) {
//                if (locationResponse.getParentId() != null) {
//
//                    String locationEn = locationResponse.getNameEn();
//                    String locationBn = locationResponse.getNameBn();
//
//                    LocationResponse district = locationService.getDataById(locationResponse.getParentId());
//                    if (district != null) {
//                        if (district.getParentId() != null) {
//
//                            locationEn = locationResponse.getNameEn() + district.getNameEn();
//                            locationBn = locationResponse.getNameBn() + district.getNameBn();
//
//                            LocationResponse division = locationService.getDataById(district.getParentId());
//                            if (division != null) {
//                                locationEn = locationResponse.getNameEn() + ", " + district.getNameEn() + ", "
//                                        + division.getNameEn();
//                                locationBn = locationResponse.getNameBn() + ", " + district.getNameBn() + ", "
//                                        + division.getNameBn();
//                            }
//                        }
//                    }
//
//                    response.setLocationEn(locationEn);
//                    response.setLocationEn(locationBn);
//                }
//            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    public LocationResponse getLocationById(Long id) {

        Location existingData = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        LocationResponse response = new LocationResponse();
        BeanUtils.copyProperties(existingData, response);

        StatusResponse statusResponse = getStatusById(existingData.getLocationTypeId());

        if (statusResponse != null) {
            response.setLocationTypeNameEn(statusResponse.getNameEn());
            response.setLocationTypeNameBn(statusResponse.getNameBn());
        }

        if (existingData.getParentId() != null) {

            Optional<Location> location = locationRepository.findById(existingData.getParentId());

            if (location != null) {
                LocationResponse locationResponse = new LocationResponse();
                BeanUtils.copyProperties(location.get(), locationResponse);
                response.setParentLocation(locationResponse);
            }
        }

        return response;
    }

    public StatusResponse getStatusById(Long id) {
        Optional<Status> existingData = statusRepository.findById(id);

        if (!existingData.isPresent()) {
            return null;
        }

        StatusResponse response = new StatusResponse();
        BeanUtils.copyProperties(existingData, response);
        return response;
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
