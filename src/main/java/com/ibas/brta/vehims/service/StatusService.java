package com.ibas.brta.vehims.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.iservice.IStatus;
import com.ibas.brta.vehims.model.Status;
import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.payload.request.StatusRequest;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.StatusGroupResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.repository.StatusRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatusService implements IStatus {

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    StatusGroupService statusGroupService;

    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);

    @Override
    public List<Status> findAllStatusGroups() {
        return statusRepository.findAll();
    }

    public PagedResponse<StatusResponse> findAllBySearch(String nameEn, Long statusGroupId, Boolean isActive, int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<Status> records = statusRepository.findStatusBySearch(nameEn, statusGroupId, isActive, pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<StatusResponse> responseData = records.map(record -> {
            // return ModelMapper.StatusToResponse(record);

            StatusResponse response = new StatusResponse();
            BeanUtils.copyProperties(record, response);

            if (record.getStatusGroupId() != null) {
                Optional<StatusGroup> statusGroup = statusGroupService.findStatusGroupById(record.getStatusGroupId());
                if (statusGroup.isPresent()) {
                    StatusGroupResponse statusGroupResponse = new StatusGroupResponse();
                    BeanUtils.copyProperties(statusGroup.get(), statusGroupResponse);
                    response.setStatusGroup(statusGroupResponse);
                } else {
                    // Handle the case when StatusGroup is not found
                    log.error("StatusGroup with id {} not found", record.getStatusGroupId());
                }
            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    public StatusResponse findStatusById(Long id) {
        Status existingData = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        StatusResponse response = new StatusResponse();
        BeanUtils.copyProperties(existingData, response);
        return response;
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

            existingData.setStatusGroupId(status.getStatusGroupId());
            existingData.setStatusCode(status.getStatusCode());
            existingData.setNameEn(status.getNameEn());
            existingData.setNameBn(status.getNameBn());
            existingData.setColorName(status.getColorName());
            existingData.setPriority(status.getPriority());
            existingData.setIsActive(status.getIsActive());

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

    public List<Status> getAllList() {
        return statusRepository.findAllByOrderByNameEnAsc();
    }

    public List<?> getActiveList() {
        // List<Status> listData =
        // statusRepository.findByIsActiveTrueOrderByNameEnAsc();
        List<Status> listData = statusRepository.findByIsActiveTrueOrderByCreatedAtDescPriorityAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(item -> {
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
