package com.ibas.brta.vehims.service;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.iservice.IStatusGroup;
import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.StatusGroupResponse;
import com.ibas.brta.vehims.repository.StatusGroupRepository;
import com.ibas.brta.vehims.util.ModelMapper;

@Service
public class StatusGroupService implements IStatusGroup {
    @Autowired
    private StatusGroupRepository statusGroupRepository;

    @Override
    public List<StatusGroup> findAllStatusGroups() {
        return statusGroupRepository.findAll();
    }

    @Override
    public List<StatusGroup> getActiveStatusGroups() {
        return statusGroupRepository.getActiveStatusGroups();
    }

    @Override
    public PagedResponse<StatusGroupResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<StatusGroup> records = statusGroupRepository.findStatusGroupBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<StatusGroupResponse> responseData = records.map(record -> {
            return ModelMapper.StatusGroupToResponse(record);
            // Create a new StatusGroupResponse object
            // StatusGroupResponse statusGroupResponse = new StatusGroupResponse();

            // // Copy properties from the entity (record) to the response object
            // BeanUtils.copyProperties(record, statusGroupResponse);

            // return statusGroupResponse;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    @Override
    public StatusGroup findStatusGroupById(Long id) {
        return statusGroupRepository.findById(id).orElse(null);
    }

    @Override
    public StatusGroup findStatusGroupByStatusGroupCode(String statusGroupCode) {
        return statusGroupRepository.findByStatusGroupCode(statusGroupCode);
    }

    @Override
    public StatusGroup createStatusGroup(StatusGroup statusGroup) {
        return statusGroupRepository.save(statusGroup);
    }

    @Override
    public StatusGroup updateStatusGroup(StatusGroup statusGroup) {
        StatusGroup existingData = statusGroupRepository.findById(statusGroup.getId())
                .orElseThrow(() -> new ResourceNotFoundException("StatusGroup", "id", statusGroup.getId()));
        // copy fields to the existing entity and save
        existingData.setStatusGroupCode(statusGroup.getStatusGroupCode());
        existingData.setNameEn(statusGroup.getNameEn());
        existingData.setNameBn(statusGroup.getNameBn());
        existingData.setIsActive(statusGroup.getIsActive());

        return statusGroupRepository.save(existingData);
    }

    @Override
    public void deleteStatusGroupById(Long id) {
        statusGroupRepository.deleteById(id);
    }

    @Override
    public void deleteStatusGroupByStatusGroupCode(String statusGroupCode) {
        statusGroupRepository.deleteByStatusGroupCode(statusGroupCode);
    }

}
