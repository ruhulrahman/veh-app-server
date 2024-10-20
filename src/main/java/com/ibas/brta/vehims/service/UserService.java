package com.ibas.brta.vehims.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.exception.FieldValidationException;
import com.ibas.brta.vehims.model.SUser;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.model.UserOfficeRole;
import com.ibas.brta.vehims.payload.request.ChangePasswordRequest;
import com.ibas.brta.vehims.payload.request.SUserRequest;
import com.ibas.brta.vehims.payload.request.SUserUpdateRequest;
import com.ibas.brta.vehims.payload.response.SUserResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.payload.response.UserOfficeRoleResponse;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.repository.UserRepository;
import com.ibas.brta.vehims.repository.UserOfficeRoleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DesignationService designationService;

    @Autowired
    StatusService statusService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserOfficeRoleRepository userOfficeRoleRepository;

    @Autowired
    EntityManager entityManager;

    // Create or Insert operation
    public SUserResponse createData(SUserRequest request) {

        request.setIsProfileCompleted(true);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User requestObject = new User();
        BeanUtils.copyProperties(request, requestObject);
        User savedData = userRepository.save(requestObject);

        // Save new roles
        request.getUserOfficeRoles().forEach(userOfficeRole -> {

            UserOfficeRole userOfficeRoleRequest = new UserOfficeRole();
            BeanUtils.copyProperties(userOfficeRole, userOfficeRoleRequest);

            userOfficeRoleRequest.setUserId(savedData.getId());
            userOfficeRoleRepository.save(userOfficeRoleRequest);
        });

        SUserResponse response = new SUserResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    @Transactional
    public SUserResponse updateData(Long id, SUserUpdateRequest request) {

        Optional<User> existingData = userRepository.findById(id);

        if (existingData.isPresent()) {

            User requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            userOfficeRoleRepository.deleteAllByUserId(requestObject.getId());
            // Delete existing roles and flush the EntityManager
            userOfficeRoleRepository.deleteAllByUserId(requestObject.getId());
            entityManager.flush(); // Ensure delete operation is executed immediately

            // Save new roles
            request.getUserOfficeRoles().forEach(userOfficeRole -> {

                UserOfficeRole userOfficeRoleRequest = new UserOfficeRole();
                BeanUtils.copyProperties(userOfficeRole, userOfficeRoleRequest);

                userOfficeRoleRequest.setUserId(requestObject.getId());
                userOfficeRoleRepository.save(userOfficeRoleRequest);
            });

            User updatedData = userRepository.save(requestObject);

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

        User existingData = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        SUserResponse response = new SUserResponse();
        BeanUtils.copyProperties(existingData, response);

        if (existingData.getUserTypeId() != null) {
            StatusResponse statusResponse = statusService.getDataById(existingData.getUserTypeId());
            response.setUserTypeNameEn(statusResponse.getNameEn());
            response.setUserTypeNameBn(statusResponse.getNameBn());
            response.setUserTypeCode(statusResponse.getStatusCode());
        }

        if (existingData.getDesignationId() != null) {
            DesignationResponse designationResponse = designationService.getDataById(existingData.getDesignationId());
            response.setDesignationNameEn(designationResponse.getNameEn());
            response.setDesignationNameBn(designationResponse.getNameBn());
        }

        List<UserOfficeRole> userOfficeRoles = userOfficeRoleRepository.findByUserId(id);

        if (!userOfficeRoles.isEmpty()) {
            List<UserOfficeRoleResponse> userOfficeRoleResponses = userOfficeRoles.stream().map(userOfficeRole -> {
                UserOfficeRoleResponse officeRoleResponse = new UserOfficeRoleResponse();
                BeanUtils.copyProperties(userOfficeRole, officeRoleResponse);
                return officeRoleResponse;
            }).collect(Collectors.toList());

            response.setUserOfficeRoles(userOfficeRoleResponses);
        } else {
            response.setUserOfficeRoles(Collections.emptyList());
        }

        return response;
    }

    public void changeUserPassword(Long id, @Valid ChangePasswordRequest request) {
        userRepository.findById(id).ifPresent(user -> {

            Map<String, String> errors = new HashMap<>();

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                errors.put("currentPassword", "Current password is incorrect");
            }

            if (request.getCurrentPassword().equals(request.getNewPassword())) {
                errors.put("newPassword", "New password cannot be the same as the current password");
            }

            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                errors.put("confirmPassword", "Passwords do not match");
            }

            // Add other generic validation errors if needed
            if (!errors.isEmpty()) {
                throw new FieldValidationException(errors); // Throw generic field validation exception
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        });
    }

}
