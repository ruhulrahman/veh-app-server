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

import com.ibas.brta.vehims.configurations.model.Gender;
import com.ibas.brta.vehims.configurations.payload.request.GenderRequest;
import com.ibas.brta.vehims.configurations.payload.response.GenderResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.GenderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GenderService {
    @Autowired
    private GenderRepository genderRepository;

    // Create or Insert operation
    public GenderResponse createData(GenderRequest genderRequest) {

        Gender gender = new Gender();
        BeanUtils.copyProperties(genderRequest, gender);
        Gender savedData = genderRepository.save(gender);

        GenderResponse response = new GenderResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public GenderResponse updateData(Long id, GenderRequest genderRequest) {
        Optional<Gender> existingData = genderRepository.findById(id);
        if (existingData.isPresent()) {
            Gender gender = existingData.get();
            BeanUtils.copyProperties(genderRequest, gender); // Exclude ID

            Gender updatedData = genderRepository.save(gender);

            GenderResponse response = new GenderResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (genderRepository.existsById(id)) {
            genderRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<GenderResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Gender> records = genderRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<GenderResponse> responseData = records.map(record -> {
            GenderResponse response = new GenderResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public GenderResponse getDataById(Long id) {

        Gender existingData = genderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        GenderResponse response = new GenderResponse();
        BeanUtils.copyProperties(existingData, response);
        return response;
    }

    public List<?> getActiveList() {
        List<Gender> entities = genderRepository.getActiveGengerList();

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
