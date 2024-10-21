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

import com.ibas.brta.vehims.model.VehicleInfo;
import com.ibas.brta.vehims.payload.request.VehicleRegPage1Request;
import com.ibas.brta.vehims.payload.request.VehicleRegPage2Request;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.VehicleInfoResponse;
import com.ibas.brta.vehims.repository.VehicleInfoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleInfoService {

    @Autowired
    VehicleInfoRepository vehicleInfoRepository;

    @Autowired
    CountryService countryService;

    // Create or Insert operation
    public VehicleInfoResponse storeVehicleRegPage1(VehicleRegPage1Request request) {

        Optional<VehicleInfo> existingData = vehicleInfoRepository.findById(request.getId());

        if (existingData.isPresent()) {
            VehicleInfo requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleInfo updatedData = vehicleInfoRepository.save(requestObject);

            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            VehicleInfo requestObject = new VehicleInfo();
            BeanUtils.copyProperties(request, requestObject);
            VehicleInfo savedData = vehicleInfoRepository.save(requestObject);

            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(savedData, response);
            return response;
        }
    }

    public VehicleInfoResponse storeVehicleRegPage2(VehicleRegPage2Request request) {

        Optional<VehicleInfo> existingData = vehicleInfoRepository.findById(request.getId());

        if (existingData.isPresent()) {
            VehicleInfo requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleInfo updatedData = vehicleInfoRepository.save(requestObject);

            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getId());
        }
    }

    // Update operation
    public VehicleInfoResponse updateData(Long id, VehicleRegPage1Request request) {

        Optional<VehicleInfo> existingData = vehicleInfoRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleInfo requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleInfo updatedData = vehicleInfoRepository.save(requestObject);

            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (vehicleInfoRepository.existsById(id)) {
            vehicleInfoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleInfoResponse> findAllBySearch(
            String chassisNumber, String engineNumber,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleInfoResponse> records = vehicleInfoRepository.findListWithPaginationBySearchWithNativeQuery(
                chassisNumber,
                engineNumber,
                pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleInfoResponse> responseData = records.map(record -> {
            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(record, response);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleInfoResponse getDataById(Long id) {

        VehicleInfo existingData = vehicleInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleInfoResponse response = new VehicleInfoResponse();
        BeanUtils.copyProperties(existingData, response);

        // if (response.getParentId() != null) {
        // Optional<VehicleInfo> parent =
        // vehicleInfoRepository.findById(response.getParentId());
        // if (parent.isPresent()) {
        // response.setParentName(parent.get().getNameEn());
        // }
        // }

        return response;
    }

}
