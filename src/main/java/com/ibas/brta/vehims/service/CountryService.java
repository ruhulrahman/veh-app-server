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

import com.ibas.brta.vehims.iservice.ICountry;
import com.ibas.brta.vehims.model.configurations.Country;
import com.ibas.brta.vehims.payload.request.CountryDTO;
import com.ibas.brta.vehims.payload.response.CountryResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.CountryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CountryService implements ICountry {
    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    // Find a single record by ID
    public CountryResponse getDataById(Long id) {

        Country existingData = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        CountryResponse response = new CountryResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    // Create or Insert operation
    public Country createData(CountryDTO countryDTO) {
        Country country = new Country();
        BeanUtils.copyProperties(countryDTO, country);
        return countryRepository.save(country);
    }

    // Update operation
    public Country updateData(Long id, CountryDTO countryDTO) {
        Optional<Country> existingData = countryRepository.findById(id);
        if (existingData.isPresent()) {
            Country country = existingData.get();
            BeanUtils.copyProperties(countryDTO, country); // Exclude ID
            return countryRepository.save(country);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<CountryResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Country> records = countryRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<CountryResponse> responseData = records.map(record -> {
            CountryResponse response = new CountryResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    public List<?> getActiveList() {
        List<Country> entities = countryRepository.findByIsActiveTrueOrderByNameEnAsc();

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
