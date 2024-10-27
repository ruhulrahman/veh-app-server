package com.ibas.brta.vehims.service;

import com.ibas.brta.vehims.exception.BadRequestException;
import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.iservice.IDesignation;
import com.ibas.brta.vehims.model.configurations.Designation;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.DesignationRepository;
import com.ibas.brta.vehims.util.AppConstants;
import com.ibas.brta.vehims.util.ModelMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DesignationService implements IDesignation {
    @Autowired
    DesignationRepository designationRepository;
    private static final Logger logger = LoggerFactory.getLogger(DesignationService.class);

    @Override
    public List<Designation> findAllDesignations() {
        return designationRepository.findAll(Sort.by("ASC", "createdDate"));
    }

    public List<Designation> findAllDesignationsOrderByLevelNumberAsc() {
        return designationRepository.findAll(Sort.by(Sort.Direction.ASC, "levelNumber"));
    }

    public List<Designation> getDesignListByNameEn(String name) {
        return designationRepository.findByNameEn(name);
    }

    public PagedResponse<DesignationResponse> getDesignationListBySearch(String nameEn, Boolean isActive, int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve all records from the database
        Page<Designation> records = designationRepository.getDesignationBySearch(nameEn, isActive, pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(),
                    records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<DesignationResponse> responseData = records.map(record -> {
            return ModelMapper.DesignationToResponse(record);
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    public PagedResponse<DesignationResponse> getDesignationBySearch(String nameEn, Boolean isActive, int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve all records from the database
        // Page<Designation> records =
        // designationRepository.getDesignationBySearch(nameEn, isActive, pageable);
        // List<Designation> recordsT = designationRepository.findAll();

        // Page<Designation> recordsT =
        // designationRepository.getDesignationBySearch(nameEn, isActive, pageable);

        // logger.info("recordsT" + recordsT.size());
        // Page<Designation> records = new PageImpl<Designation>(recordsT, pageable,
        // recordsT.size());
        // Page<Designation> records = new PageImpl<Designation>(recordsT, pageable,
        // recordsT.size());
        // Retrieve
        Page<Designation> records = designationRepository.getDesignationBySearch(nameEn, isActive, pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(),
                    records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<DesignationResponse> responseData = records.map(record -> {
            return ModelMapper.DesignationToResponse(record);
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    // @Override
    // public PagedResponse<VehdataResponse> searchByRegistrationNo(int page, int
    // size, String searchkey) {
    // validatePageNumberAndSize(page, size);

    // // Retrieve
    // Pageable pageable = PageRequest.of(page, size);
    // Page<VehicleData> records =
    // vehicleDataRepository.searchByRegistrationNo(searchkey.toUpperCase(),
    // pageable);

    // if (records.getNumberOfElements() == 0) {
    // return new PagedResponse<>(Collections.emptyList(), records.getNumber(),
    // records.getSize(), records.getTotalElements(), records.getTotalPages(),
    // records.isLast());
    // }

    // // Map Responses with all information

    // List<VehdataResponse> responseData = records.map(record -> {
    // return ModelMapper.VehicledataToResponse(record);
    // }).getContent();

    // return new PagedResponse<>(responseData, records.getNumber(),
    // records.getSize(), records.getTotalElements(), records.getTotalPages(),
    // records.isLast());

    // }

    @Override
    public Optional<Designation> findDesignationById(Long id) {
        return designationRepository.findById(id);
    }

    @Transactional
    @Override
    public Designation saveDesignation(Designation designation) {
        return designationRepository.save(designation);
    }

    @Transactional
    @Override
    public Designation updateDesignation(Designation designation) {

        Designation existingDesignation = designationRepository.findById(designation.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Designation", "id", designation.getId()));
        // copy fields to the existing entity and save
        existingDesignation.setNameEn(designation.getNameEn());
        existingDesignation.setNameBn(designation.getNameBn());
        existingDesignation.setLevelNumber(designation.getLevelNumber());
        existingDesignation.setParentDesignationId(designation.getParentDesignationId());
        existingDesignation.setIsActive(designation.getIsActive());

        return designationRepository.save(existingDesignation);
    }

    public List<Designation> getParentDesignationList() {
        // return designationRepository.findByParentDesignationIdIsNull();
        return designationRepository.findByParentDesignationIdIsNullOrderByLevelNumberAsc();
    }

    public List<DesignationResponse> getDesignationListIsActiveTrue() {

        List<Designation> records = designationRepository.findByIsActiveTrueOrderByLevelNumberAsc();
        // Map Responses with all information
        List<DesignationResponse> responseData = records.stream().map(record -> {
            // return ModelMapper.VehicleTypeToResponse(record);
            DesignationResponse response = new DesignationResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).collect(Collectors.toList());

        return responseData;
    }

    @Override
    public void deleteDesignationById(Long id) {
        designationRepository.deleteById(id);
    }

    public DesignationResponse getDataById(Long id) {
        Designation existingData = designationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        DesignationResponse response = new DesignationResponse();
        BeanUtils.copyProperties(existingData, response);
        return response;
    }

    public List<?> getActiveList() {
        List<Designation> entities = designationRepository.findByIsActiveTrueOrderByNameEnAsc();

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
