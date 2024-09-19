package com.ibas.brta.vehims.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.controller.StatusController;
import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.iservice.IStatus;
import com.ibas.brta.vehims.model.Status;
import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.payload.request.StatusRequest;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.repository.StatusRepository;
import com.ibas.brta.vehims.util.ModelMapper;

@Service
public class StatusService implements IStatus {

    @Autowired
    private StatusRepository statusRepository;

    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);

    @Override
    public List<Status> findAllStatusGroups() {
        return statusRepository.findAll();
    }

    @Override
    public PagedResponse<StatusResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Status> records = statusRepository.findStatusBySearch(nameEn, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<StatusResponse> responseData = records.map(record -> {
            return ModelMapper.StatusToResponse(record);
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    @Override
    public Status findStatusById(Long id) {
        return statusRepository.findById(id).orElse(null);
    }

    @Override
    public Status findStatusByStatusCode(String statusCode) {
        return statusRepository.findByStatusCode(statusCode);
    }

    @Override
    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status updateStatus(StatusRequest status) {

        try {

            Status existingData = statusRepository.findById(status.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Status", "id",
                            status.getId()));

            logger.info("Existing data: ", existingData.toString());

            existingData.setStatusCode(status.getStatusCode());
            existingData.setStatusGroupId(status.getStatusGroupId());
            existingData.setNameEn(status.getNameEn());
            existingData.setNameBn(status.getNameBn());
            existingData.setColorName(status.getColorName());
            existingData.setPriority(status.getPriority());
            existingData.setIsActive(!existingData.getIsActive());

            return statusRepository.save(existingData);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("error=====", e);
        }
        return new Status();
    }

    @Override
    public void deleteStatusById(Long id) {
        statusRepository.deleteById(id);
    }

    @Override
    public void deleteStatusByStatusCode(String statusCode) {
        statusRepository.deleteByStatusCode(statusCode);
    }

}
