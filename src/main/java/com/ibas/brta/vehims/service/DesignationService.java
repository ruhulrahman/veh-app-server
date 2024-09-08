package com.ibas.brta.vehims.service;

import com.ibas.brta.vehims.iservice.IDesignation;
import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.payload.request.DesignationRequest;
import com.ibas.brta.vehims.repository.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService implements IDesignation {
    @Autowired
    DesignationRepository designationRepository;

    @Override
    public List<Designation> findAllDesignations() {
        return designationRepository.findAll();
    }

    @Override
    public Page<Designation> findAllDesignationsWithPagination(DesignationRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        // If there's a search term, filter by name
        if (request.getNameEn() != null && !request.getNameEn().isEmpty()) {
            return designationRepository.findByNameEnContainingIgnoreCase(request.getNameEn(), pageable);
        }

        // If there's an isActive filter
        if (request.getIsActive() != null && !request.getIsActive().isEmpty()) {
            return designationRepository.findByIsActive(request.getIsActive(), pageable);
        }

        return designationRepository.findAll(pageable);
    }

    @Override
    public Optional<Designation> findDesignationById(Long id) {
        return designationRepository.findById(id);
    }

    @Override
    public Designation saveDesignation(Designation designation) {
        return designationRepository.save(designation);
    }
}
