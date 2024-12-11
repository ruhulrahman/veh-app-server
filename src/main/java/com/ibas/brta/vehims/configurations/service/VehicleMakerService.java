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

import com.ibas.brta.vehims.configurations.model.VehicleMaker;
import com.ibas.brta.vehims.configurations.payload.request.VehicleMakerRequest;
import com.ibas.brta.vehims.configurations.payload.response.VehicleMakerResponse;
import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.VehicleMakerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleMakerService {

    @Autowired
    VehicleMakerRepository vehicleMakerRepository;

    @Autowired
    CountryService countryService;

    // Create or Insert operation
    public VehicleMakerResponse createData(VehicleMakerRequest request) {

        VehicleMaker requestObject = new VehicleMaker();
        BeanUtils.copyProperties(request, requestObject);
        VehicleMaker savedData = vehicleMakerRepository.save(requestObject);

        VehicleMakerResponse response = new VehicleMakerResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleMakerResponse updateData(Long id, VehicleMakerRequest request) {

        Optional<VehicleMaker> existingData = vehicleMakerRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleMaker requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleMaker updatedData = vehicleMakerRepository.save(requestObject);

            VehicleMakerResponse response = new VehicleMakerResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (vehicleMakerRepository.existsById(id)) {
            vehicleMakerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleMakerResponse> findAllBySearch(String nameEn, Long countryId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleMaker> records = vehicleMakerRepository.findListWithPaginationBySearch(nameEn,
                countryId,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleMakerResponse> responseData = records.map(record -> {
            VehicleMakerResponse response = new VehicleMakerResponse();
            BeanUtils.copyProperties(record, response);

            CountryResponse countryResponse = countryService.getDataById(record.getCountryId());
            response.setCountry(countryResponse);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleMakerResponse getDataById(Long id) {

        VehicleMaker existingData = vehicleMakerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleMakerResponse response = new VehicleMakerResponse();
        BeanUtils.copyProperties(existingData, response);

        CountryResponse countryResponse = countryService.getDataById(existingData.getCountryId());
        response.setCountry(countryResponse);

        return response;
    }

    public List<?> getActiveList() {
        List<VehicleMaker> listData = vehicleMakerRepository.findByIsActiveTrueOrderByNameEnAsc();

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
