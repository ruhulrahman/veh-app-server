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

import com.ibas.brta.vehims.model.configurations.VehicleBrand;
import com.ibas.brta.vehims.payload.request.VehicleBrandRequest;
import com.ibas.brta.vehims.payload.response.VehicleBrandResponse;
import com.ibas.brta.vehims.payload.response.VehicleMakerResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.VehicleBrandRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleBrandService {

    @Autowired
    VehicleBrandRepository vehicleBrandRepository;

    @Autowired
    VehicleMakerService vehicleMakerService;

    // Create or Insert operation
    public VehicleBrandResponse createData(VehicleBrandRequest request) {

        VehicleBrand requestObject = new VehicleBrand();
        BeanUtils.copyProperties(request, requestObject);
        VehicleBrand savedData = vehicleBrandRepository.save(requestObject);

        VehicleBrandResponse response = new VehicleBrandResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleBrandResponse updateData(Long id, VehicleBrandRequest request) {

        Optional<VehicleBrand> existingData = vehicleBrandRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleBrand requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleBrand updatedData = vehicleBrandRepository.save(requestObject);

            VehicleBrandResponse response = new VehicleBrandResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (vehicleBrandRepository.existsById(id)) {
            vehicleBrandRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleBrandResponse> findAllBySearch(String nameEn, Long makerId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleBrand> records = vehicleBrandRepository.findListWithPaginationBySearch(nameEn,
                makerId,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleBrandResponse> responseData = records.map(record -> {
            VehicleBrandResponse response = new VehicleBrandResponse();
            BeanUtils.copyProperties(record, response);

            VehicleMakerResponse countryResponse = vehicleMakerService.getDataById(record.getMakerId());
            response.setMaker(countryResponse);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleBrandResponse getDataById(Long id) {

        VehicleBrand existingData = vehicleBrandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleBrandResponse response = new VehicleBrandResponse();
        BeanUtils.copyProperties(existingData, response);

        VehicleMakerResponse countryResponse = vehicleMakerService.getDataById(existingData.getMakerId());
        response.setMaker(countryResponse);

        return response;
    }

    public List<?> getActiveList() {
        List<VehicleBrand> listData = vehicleBrandRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("nameBn", item.getNameBn());
            object.put("makerId", item.getMakerId());

            customArray.add(object);
        });

        return customArray;
    }
}
