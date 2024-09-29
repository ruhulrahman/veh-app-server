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

import com.ibas.brta.vehims.model.VehicleColor;
import com.ibas.brta.vehims.payload.request.VehicleColorDTO;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.VehicleColorResponse;
import com.ibas.brta.vehims.repository.VehicleColorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehicleColorService {
    @Autowired
    private VehicleColorRepository vehicleColorRepository;

    // Create or Insert operation
    public VehicleColor createData(VehicleColorDTO vehicleColorDTO) {
        VehicleColor vehicleColor = new VehicleColor();
        BeanUtils.copyProperties(vehicleColorDTO, vehicleColor);
        return vehicleColorRepository.save(vehicleColor);
    }

    // Update operation
    public VehicleColor updateData(Long id, VehicleColorDTO vehicleColorDTO) {
        Optional<VehicleColor> existingData = vehicleColorRepository.findById(id);
        if (existingData.isPresent()) {
            VehicleColor vehicleColor = existingData.get();
            BeanUtils.copyProperties(vehicleColorDTO, vehicleColor); // Exclude ID
            return vehicleColorRepository.save(vehicleColor);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteDataById(Long id) {
        if (vehicleColorRepository.existsById(id)) {
            vehicleColorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleColorResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleColor> records = vehicleColorRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleColorResponse> responseData = records.map(record -> {
            // return ModelMapper.VehicleTypeToResponse(record);
            VehicleColorResponse response = new VehicleColorResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleColor getDataById(Long id) {
        return vehicleColorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));
    }

    public List<VehicleColor> getAllList() {
        return vehicleColorRepository.findAllByOrderByNameEnAsc();
    }

    public List<?> getActiveList() {
        List<VehicleColor> entities = vehicleColorRepository.findByIsActiveTrueOrderByNameEnAsc();

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
