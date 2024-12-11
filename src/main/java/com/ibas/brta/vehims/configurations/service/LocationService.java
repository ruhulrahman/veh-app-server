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

import com.ibas.brta.vehims.configurations.model.Location;
import com.ibas.brta.vehims.configurations.payload.request.LocationRequest;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import com.ibas.brta.vehims.configurations.repository.LocationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    StatusService statusService;

    // Create or Insert operation
    public LocationResponse createData(LocationRequest request) {

        Location requestObject = new Location();
        BeanUtils.copyProperties(request, requestObject);
        Location savedData = locationRepository.save(requestObject);

        LocationResponse response = new LocationResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public LocationResponse updateData(Long id, LocationRequest request) {

        Optional<Location> existingData = locationRepository.findById(id);

        if (existingData.isPresent()) {
            Location requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            Location updatedData = locationRepository.save(requestObject);

            LocationResponse response = new LocationResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<LocationResponse> findAllBySearch(
            String nameEn, Long locationTypeId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Location> records = locationRepository.findListWithPaginationBySearch(
                nameEn,
                locationTypeId,
                isActive,
                pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<LocationResponse> responseData = records.map(record -> {
            LocationResponse response = new LocationResponse();
            BeanUtils.copyProperties(record, response);

            StatusResponse statusResponse = statusService.findStatusById(record.getLocationTypeId());

            if (statusResponse != null) {
                response.setLocationTypeNameEn(statusResponse.getNameEn());
                response.setLocationTypeNameBn(statusResponse.getNameBn());
            }

            LocationResponse locationResponse = this.getDataById(record.getParentId());

            if (locationResponse != null) {
                response.setParentLocation(locationResponse);
            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public LocationResponse getDataById(Long id) {

        Location existingData = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        LocationResponse response = new LocationResponse();
        BeanUtils.copyProperties(existingData, response);

        StatusResponse statusResponse = statusService.findStatusById(existingData.getLocationTypeId());

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

    public List<?> getActiveList() {
        List<Location> listData = locationRepository.findByIsActiveTrueOrderByNameEnAsc();

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
