package com.ibas.brta.vehims.service;

import com.ibas.brta.vehims.iservice.IDesignation;
import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.repository.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<Designation> findDesignationById(Long id) {
        return designationRepository.findById(id);
    }

    @Override
    public Designation saveDesignation(Designation designation) {
        return designationRepository.save(designation);
    }
}
