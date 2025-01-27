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

import com.ibas.brta.vehims.configurations.model.DocumentType;
import com.ibas.brta.vehims.configurations.payload.request.DocumentTypeRequest;
import com.ibas.brta.vehims.configurations.payload.response.DocumentTypeResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.DocumentTypeRepository;
import com.ibas.brta.vehims.exception.FieldValidationException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DocumentTypeService {
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    // Create or Insert operation
    public DocumentTypeResponse createData(DocumentTypeRequest request) {

        if (documentTypeRepository.existsByNameEn(request.getNameEn())) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Document Type with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }

        DocumentType requestObject = new DocumentType();
        BeanUtils.copyProperties(request, requestObject);
        DocumentType savedData = documentTypeRepository.save(requestObject);

        DocumentTypeResponse response = new DocumentTypeResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public DocumentTypeResponse updateData(Long id, DocumentTypeRequest request) {

        if (documentTypeRepository.existsByNameEnAndIdNot(request.getNameEn(), id)) {
            Map<String, String> errors = new HashMap<>();

            errors.put("nameEn", "Document Type with name " + request.getNameEn() + " already exists.");

            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors);
            }
        }

        Optional<DocumentType> existingData = documentTypeRepository.findById(id);

        if (existingData.isPresent()) {
            DocumentType requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            DocumentType updatedData = documentTypeRepository.save(requestObject);

            DocumentTypeResponse response = new DocumentTypeResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (documentTypeRepository.existsById(id)) {
            documentTypeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<DocumentTypeResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<DocumentType> records = documentTypeRepository.findListWithPaginationBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<DocumentTypeResponse> responseData = records.map(record -> {
            DocumentTypeResponse response = new DocumentTypeResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public DocumentTypeResponse getDataById(Long id) {

        DocumentType existingData = documentTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        DocumentTypeResponse response = new DocumentTypeResponse();
        BeanUtils.copyProperties(existingData, response);
        return response;
    }

    public List<?> getActiveList() {
        List<DocumentType> listData = documentTypeRepository.findByIsActiveTrueOrderByNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", item.getId());
            object.put("nameEn", item.getNameEn());
            object.put("nameBn", item.getNameBn());
            object.put("documentFormat", item.getDocumentFormat());
            object.put("documentSize", item.getDocumentSize());

            customArray.add(object);
        });

        return customArray;
    }
}
