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

import com.ibas.brta.vehims.model.VehicleRoute;
import com.ibas.brta.vehims.payload.request.VehicleRouteRequest;
import com.ibas.brta.vehims.payload.response.VehicleRouteResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.repository.VehicleRouteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleRouteService {

    @Autowired
    VehicleRouteRepository vehicleRouteRepository;

    @Autowired
    StatusService statusService;

    // Create or Insert operation
    public VehicleRouteResponse createData(VehicleRouteRequest request) {

        VehicleRoute requestObject = new VehicleRoute();
        BeanUtils.copyProperties(request, requestObject);
        VehicleRoute savedData = vehicleRouteRepository.save(requestObject);

        VehicleRouteResponse response = new VehicleRouteResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleRouteResponse updateData(Long id, VehicleRouteRequest request) {

        Optional<VehicleRoute> existingData = vehicleRouteRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleRoute requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleRoute updatedData = vehicleRouteRepository.save(requestObject);

            VehicleRouteResponse response = new VehicleRouteResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (vehicleRouteRepository.existsById(id)) {
            vehicleRouteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleRouteResponse> findAllBySearch(String nameEn, Long routePermitTypeId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleRoute> records = vehicleRouteRepository.findListWithPaginationBySearch(nameEn,
                routePermitTypeId,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleRouteResponse> responseData = records.map(record -> {
            VehicleRouteResponse response = new VehicleRouteResponse();
            BeanUtils.copyProperties(record, response);
            StatusResponse statusResponse = statusService.findStatusById(record.getRoutePermitTypeId());
            response.setRoutePermitType(statusResponse);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleRouteResponse getDataById(Long id) {

        VehicleRoute existingData = vehicleRouteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleRouteResponse response = new VehicleRouteResponse();
        BeanUtils.copyProperties(existingData, response);

        StatusResponse statusResponse = statusService.findStatusById(existingData.getRoutePermitTypeId());
        response.setRoutePermitType(statusResponse);

        return response;
    }

    public List<?> getActiveList() {
        List<VehicleRoute> listData = vehicleRouteRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("nameBn", item.getNameBn());
            object.put("minDistrict", item.getMinDistrict());
            object.put("maxDistrict", item.getMaxDistrict());

            customArray.add(object);
        });

        return customArray;
    }
}
