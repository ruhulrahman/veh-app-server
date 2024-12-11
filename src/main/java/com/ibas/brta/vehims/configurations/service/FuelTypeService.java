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

import com.ibas.brta.vehims.configurations.model.FuelType;
import com.ibas.brta.vehims.configurations.payload.request.FuelTypeDTO;
import com.ibas.brta.vehims.configurations.payload.response.FuelTypeResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.FuelTypeRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FuelTypeService {
    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    // Create or Insert operation
    public FuelType createData(FuelTypeDTO fuelTypeDTO) {
        FuelType fuelType = new FuelType();
        BeanUtils.copyProperties(fuelTypeDTO, fuelType);
        return fuelTypeRepository.save(fuelType);
    }

    // Update operation
    public FuelType updateData(Long id, FuelTypeDTO fuelTypeDTO) {
        Optional<FuelType> existingData = fuelTypeRepository.findById(id);
        if (existingData.isPresent()) {
            FuelType fuelType = existingData.get();
            BeanUtils.copyProperties(fuelTypeDTO, fuelType); // Exclude ID
            return fuelTypeRepository.save(fuelType);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (fuelTypeRepository.existsById(id)) {
            fuelTypeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<FuelTypeResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<FuelType> records = fuelTypeRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<FuelTypeResponse> responseData = records.map(record -> {
            FuelTypeResponse response = new FuelTypeResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public FuelType getDataById(Long id) {
        return fuelTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));
    }

    public List<?> getActiveList() {
        List<FuelType> entities = fuelTypeRepository.findByIsActiveTrueOrderByNameEnAsc();

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
