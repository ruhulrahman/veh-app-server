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

import com.ibas.brta.vehims.configurations.model.VehicleRegistrationMark;
import com.ibas.brta.vehims.configurations.model.VehicleRegistrationMarkOrganizationMap;
import com.ibas.brta.vehims.configurations.payload.request.VehicleRegistrationMarkRequest;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleRegistrationMarkResponse;
import com.ibas.brta.vehims.configurations.repository.VehicleRegistrationMarkOrganizationMapRepository;
import com.ibas.brta.vehims.configurations.repository.VehicleRegistrationMarkRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleRegistrationMarkService {
    @Autowired
    private VehicleRegistrationMarkRepository vehicleRegistrationMarkRepository;

    @Autowired
    private VehicleRegistrationMarkOrganizationMapRepository vehicleRegistrationMarkOrganizationMapRepository;

    // Create or Insert operation
    @Transactional
    public VehicleRegistrationMark createData(VehicleRegistrationMarkRequest request) {

        VehicleRegistrationMark vehicleRegistrationMark = new VehicleRegistrationMark();
        BeanUtils.copyProperties(request, vehicleRegistrationMark);
        VehicleRegistrationMark savedData = vehicleRegistrationMarkRepository.save(vehicleRegistrationMark);

        vehicleRegistrationMarkOrganizationMapRepository
                .deleteByVehicleRegistrationMarkId(savedData.getId());

        for (Long organizationId : request.getOrgIds()) {
            VehicleRegistrationMarkOrganizationMap vehicleRegistrationMarkOrganizationMap = new VehicleRegistrationMarkOrganizationMap();
            vehicleRegistrationMarkOrganizationMap.setOrgId(organizationId);
            vehicleRegistrationMarkOrganizationMap.setVehicleRegistrationMarkId(savedData.getId());
            vehicleRegistrationMarkOrganizationMapRepository.save(vehicleRegistrationMarkOrganizationMap);
        }

        return savedData;
    }

    // Update operation
    @Transactional
    public VehicleRegistrationMark updateData(Long id, VehicleRegistrationMarkRequest request) {
        Optional<VehicleRegistrationMark> existingData = vehicleRegistrationMarkRepository.findById(id);
        if (existingData.isPresent()) {
            VehicleRegistrationMark vehicleRegistrationMark = existingData.get();
            BeanUtils.copyProperties(request, vehicleRegistrationMark); // Exclude ID
            VehicleRegistrationMark savedData = vehicleRegistrationMarkRepository.save(vehicleRegistrationMark);

            vehicleRegistrationMarkOrganizationMapRepository.deleteByVehicleRegistrationMarkId(savedData.getId());

            for (Long organizationId : request.getOrgIds()) {
                VehicleRegistrationMarkOrganizationMap vehicleRegistrationMarkOrganizationMap = new VehicleRegistrationMarkOrganizationMap();
                vehicleRegistrationMarkOrganizationMap.setOrgId(organizationId);
                vehicleRegistrationMarkOrganizationMap.setVehicleRegistrationMarkId(savedData.getId());
                vehicleRegistrationMarkOrganizationMapRepository.save(vehicleRegistrationMarkOrganizationMap);
            }

            return savedData;

        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteDataById(Long id) {
        if (vehicleRegistrationMarkRepository.existsById(id)) {
            vehicleRegistrationMarkOrganizationMapRepository.deleteByVehicleRegistrationMarkId(id);
            vehicleRegistrationMarkRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleRegistrationMarkResponse> findAllBySearch(String nameEn, Boolean isActive, int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleRegistrationMark> records = vehicleRegistrationMarkRepository.findListWithPaginationBySearch(nameEn,
                isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleRegistrationMarkResponse> responseData = records.map(record -> {
            List<Long> orgIds = vehicleRegistrationMarkOrganizationMapRepository
                    .findOrgIdsByVehicleRegistrationMarkId(record.getId());
            VehicleRegistrationMarkResponse response = new VehicleRegistrationMarkResponse();
            BeanUtils.copyProperties(record, response);
            response.setOrgIds(orgIds);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleRegistrationMarkResponse getDataById(Long id) {

        VehicleRegistrationMark existingData = vehicleRegistrationMarkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleRegistrationMarkResponse response = new VehicleRegistrationMarkResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public List<VehicleRegistrationMark> getAllList() {
        return vehicleRegistrationMarkRepository.findAllByOrderByNameEnAsc();
    }

    public List<?> getActiveList() {
        List<VehicleRegistrationMark> entities = vehicleRegistrationMarkRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        entities.forEach(item -> {
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
