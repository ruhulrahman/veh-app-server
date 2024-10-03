package com.ibas.brta.vehims.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.SUser;
import com.ibas.brta.vehims.payload.request.SUserRequest;
import com.ibas.brta.vehims.payload.response.SUserResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.SUserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    @Autowired
    SUserRepository userRepository;

    @Autowired
    DesignationService designationService;

    @Autowired
    StatusService statusService;

    // Create or Insert operation
    public SUserResponse createData(SUserRequest request) {

        SUser requestObject = new SUser();
        BeanUtils.copyProperties(request, requestObject);
        SUser savedData = userRepository.save(requestObject);

        SUserResponse response = new SUserResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public SUserResponse updateData(Long id, SUserRequest request) {

        Optional<SUser> existingData = userRepository.findById(id);

        if (existingData.isPresent()) {
            SUser requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            SUser updatedData = userRepository.save(requestObject);

            SUserResponse response = new SUserResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<SUserResponse> findAllBySearch(
            String nameEn, String email, String mobile, Long userTypeId, Long designationId, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<SUser> records = userRepository.findListWithPaginationBySearch(nameEn,
                email,
                mobile,
                userTypeId,
                designationId,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        log.info("records ======", records);

        // Map Responses with all information
        List<SUserResponse> responseData = records.map(record -> {
            SUserResponse response = new SUserResponse();
            BeanUtils.copyProperties(record, response);

            if (record.getUserTypeId() != null) {
                StatusResponse statusResponse = statusService.getDataById(record.getUserTypeId());
                response.setUserTypeNameEn(statusResponse.getNameEn());
                response.setUserTypeNameBn(statusResponse.getNameBn());
            }

            if (record.getDesignationId() != null) {
                DesignationResponse designationResponse = designationService.getDataById(record.getDesignationId());
                response.setDesignationNameEn(designationResponse.getNameEn());
                response.setDesignationNameBn(designationResponse.getNameBn());
            }

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public SUserResponse getDataById(Long id) {

        SUser existingData = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        SUserResponse response = new SUserResponse();
        BeanUtils.copyProperties(existingData, response);

        if (existingData.getUserTypeId() != null) {
            StatusResponse statusResponse = statusService.getDataById(existingData.getUserTypeId());
            response.setUserTypeNameEn(statusResponse.getNameEn());
            response.setUserTypeNameBn(statusResponse.getNameBn());
        }

        if (existingData.getDesignationId() != null) {
            DesignationResponse designationResponse = designationService.getDataById(existingData.getDesignationId());
            response.setDesignationNameEn(designationResponse.getNameEn());
            response.setDesignationNameBn(designationResponse.getNameBn());
        }

        return response;
    }

}
