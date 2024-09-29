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

import com.ibas.brta.vehims.model.FiscalYear;
import com.ibas.brta.vehims.payload.request.FiscalYearRequest;
import com.ibas.brta.vehims.payload.response.FiscalYearResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.FiscalYearRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FiscalYearService {

    @Autowired
    FiscalYearRepository fiscalYearRepository;

    // Create or Insert operation
    public FiscalYearResponse createData(FiscalYearRequest request) {

        FiscalYear requestObject = new FiscalYear();
        BeanUtils.copyProperties(request, requestObject);
        FiscalYear savedData = fiscalYearRepository.save(requestObject);

        FiscalYearResponse response = new FiscalYearResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public FiscalYearResponse updateData(Long id, FiscalYearRequest request) {

        Optional<FiscalYear> existingData = fiscalYearRepository.findById(id);

        if (existingData.isPresent()) {
            FiscalYear requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            FiscalYear updatedData = fiscalYearRepository.save(requestObject);

            FiscalYearResponse response = new FiscalYearResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (fiscalYearRepository.existsById(id)) {
            fiscalYearRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<FiscalYearResponse> findAllBySearch(String nameEn, Boolean isActive, int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<FiscalYear> records = fiscalYearRepository.findListWithPaginationBySearch(nameEn,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<FiscalYearResponse> responseData = records.map(record -> {
            FiscalYearResponse response = new FiscalYearResponse();
            BeanUtils.copyProperties(record, response);

            int startYear = record.getStartDate().getYear();
            int endYear = record.getEndDate().getYear();

            response.setStartYear(startYear);
            response.setEndYear(endYear);
            response.setFiscalYear(startYear + "-" + endYear);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public FiscalYearResponse getDataById(Long id) {

        FiscalYear existingData = fiscalYearRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        FiscalYearResponse response = new FiscalYearResponse();
        BeanUtils.copyProperties(existingData, response);

        int startYear = existingData.getStartDate().getYear();
        int endYear = existingData.getEndDate().getYear();

        response.setStartYear(startYear);
        response.setEndYear(endYear);
        response.setFiscalYear(startYear + "-" + endYear);

        return response;
    }

    public List<?> getActiveList() {
        List<FiscalYear> listData = fiscalYearRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("nameBn", item.getNameBn());
            object.put("startDate", item.getStartDate());
            object.put("endDate", item.getEndDate());

            int startYear = item.getStartDate().getYear();
            int endYear = item.getEndDate().getYear();

            object.put("startYear", startYear);
            object.put("endYear", endYear);
            object.put("fiscalYear", startYear + "-" + endYear);

            customArray.add(object);
        });

        return customArray;
    }
}
