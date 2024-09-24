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
import com.ibas.brta.vehims.model.VehicleType;
import com.ibas.brta.vehims.payload.request.VehicleTypeDTO;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.repository.VehicleTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehicleTypService {
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    // Create or Insert operation
    public VehicleType createData(VehicleTypeDTO vehicleTypeDTO) {
        VehicleType vehicleType = new VehicleType();
        BeanUtils.copyProperties(vehicleTypeDTO, vehicleType);
        return vehicleTypeRepository.save(vehicleType);
    }

    // Update operation
    public VehicleType updateData(Long id, VehicleTypeDTO vehicleTypeDTO) {
        Optional<VehicleType> existingData = vehicleTypeRepository.findById(id);
        if (existingData.isPresent()) {
            VehicleType vehicleType = existingData.get();
            BeanUtils.copyProperties(vehicleTypeDTO, vehicleType); // Exclude ID
            return vehicleTypeRepository.save(vehicleType);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteDataById(Long id) {
        if (vehicleTypeRepository.existsById(id)) {
            vehicleTypeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleTypeResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleType> records = vehicleTypeRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleTypeResponse> responseData = records.map(record -> {
            // return ModelMapper.VehicleTypeToResponse(record);
            VehicleTypeResponse response = new VehicleTypeResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleType getDataById(Long id) {
        return vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));
    }

    public List<VehicleType> getAllList() {
        return vehicleTypeRepository.findAllByOrderByNameEnAsc();
    }

    public List<?> getActiveList() {
        List<VehicleType> entities = vehicleTypeRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        entities.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("nameEn", serviceEntity.getNameEn());
            object.put("nameBn", serviceEntity.getNameBn());

            customArray.add(object);
        });

        return customArray;
    }
}
