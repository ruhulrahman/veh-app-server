package com.ibas.brta.vehims.userManagement.service;

import java.util.*;
import java.util.stream.Collectors;

import com.ibas.brta.vehims.configurations.service.DesignationService;
import com.ibas.brta.vehims.configurations.service.StatusService;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceMedia;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceMediaResponse;
import com.ibas.brta.vehims.userManagement.dao.UserDao;
import com.ibas.brta.vehims.userManagement.model.UserTinInfo;
import com.ibas.brta.vehims.userManagement.payload.response.UserTinInfoResponse;
import com.ibas.brta.vehims.userManagement.repository.UserTinInfoRepository;
import com.ibas.brta.vehims.util.Utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibas.brta.vehims.exception.FieldValidationException;
import com.ibas.brta.vehims.userManagement.model.SUser;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.model.UserDetail;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.model.UserOfficeRole;
import com.ibas.brta.vehims.userManagement.payload.request.ApplicantUserRequest;
import com.ibas.brta.vehims.userManagement.payload.request.ChangePasswordRequest;
import com.ibas.brta.vehims.userManagement.payload.request.SUserRequest;
import com.ibas.brta.vehims.userManagement.payload.request.SUserUpdateRequest;
import com.ibas.brta.vehims.userManagement.payload.request.UserDetailRequest;
import com.ibas.brta.vehims.userManagement.payload.request.UserProfileRequest;
import com.ibas.brta.vehims.userManagement.payload.response.UserDetailResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserFullResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserOfficeRoleResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserProfileResponse;
import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;
import com.ibas.brta.vehims.configurations.payload.response.DesignationResponse;
import com.ibas.brta.vehims.common.payload.request.MediaRequest;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.MediaResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.common.service.MediaService;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import com.ibas.brta.vehims.userManagement.repository.UserDetailRepository;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.userManagement.repository.UserOfficeRoleRepository;

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

    @Autowired
    private UserTinInfoRepository userTinInfoRepository;

    @Autowired
    private UserNidInfoRepository userNidInfoRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    MediaService mediaService;

    @Autowired
    UserDao userDao;

    // Create Applicant user account
    public UserFullResponse createApplicantUserData(ApplicantUserRequest request) {

        request.setIsProfileCompleted(false);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User requestObject = new User();
        BeanUtils.copyProperties(request, requestObject);
        User savedData = userRepository.save(requestObject);

        UserFullResponse response = new UserFullResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Create or Insert operation
    public UserFullResponse createData(SUserRequest request) {

        request.setIsProfileCompleted(true);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User requestObject = new User();
        BeanUtils.copyProperties(request, requestObject);
        requestObject.setCreatedBy(Utils.getLoggedinUserId());
        User savedData = userRepository.save(requestObject);

        // Save new roles
        request.getUserOfficeRoles().forEach(userOfficeRole -> {

            UserOfficeRole userOfficeRoleRequest = new UserOfficeRole();
            BeanUtils.copyProperties(userOfficeRole, userOfficeRoleRequest);

            userOfficeRoleRequest.setUserId(savedData.getId());
            userOfficeRoleRepository.save(userOfficeRoleRequest);
        });

        UserFullResponse response = new UserFullResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    @Transactional
    public UserFullResponse updateData(Long id, SUserUpdateRequest request) {

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

            UserFullResponse response = new UserFullResponse();
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
    public PagedResponse<UserFullResponse> findAllBySearch(
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
        List<UserFullResponse> responseData = records.map(record -> {
            UserFullResponse response = new UserFullResponse();
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
    public UserFullResponse getDataById(Long id) {

        User existingData = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        UserFullResponse response = new UserFullResponse();
        BeanUtils.copyProperties(existingData, response);

        log.info("existingData.getCreatedAt() ========= {}", existingData.getCreatedAt());

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

        List<UserOfficeRoleResponse> userOfficeRoleResponses = userDao.getUserOfficeRoles(id);
        response.setUserOfficeRoles(userOfficeRoleResponses);

//        List<UserOfficeRole> userOfficeRoles = userOfficeRoleRepository.findByUserId(id);
//
//        if (!userOfficeRoles.isEmpty()) {
//            List<UserOfficeRoleResponse> userOfficeRoleResponses = userOfficeRoles.stream().map(userOfficeRole -> {
//                UserOfficeRoleResponse officeRoleResponse = new UserOfficeRoleResponse();
//                BeanUtils.copyProperties(userOfficeRole, officeRoleResponse);
//                return officeRoleResponse;
//            }).collect(Collectors.toList());
//
//            response.setUserOfficeRoles(userOfficeRoleResponses);
//        } else {
//            response.setUserOfficeRoles(Collections.emptyList());
//        }

        return response;
    }


    // Find a single record by ID
    public UserFullResponse getAuthUserProfile() {

        User existingData = userRepository.findById(Objects.requireNonNull(Utils.getLoggedinUserId()))
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + Utils.getLoggedinUserId()));

        UserFullResponse response = new UserFullResponse();
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

        List<UserOfficeRoleResponse> userOfficeRoleResponses = userDao.getUserOfficeRoles(existingData.getId());
        response.setUserOfficeRoles(userOfficeRoleResponses);

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

    public UserTinInfoResponse getUserTinInfo(Long id) {
        UserTinInfo dbEntity = userTinInfoRepository.findByUserId(id);
        UserTinInfoResponse obj = new UserTinInfoResponse();
        if (dbEntity != null) {
            BeanUtils.copyProperties(dbEntity, obj);
        }
        return obj;
    }

    public UserNidInfoResponse getUserNidInfo(Long id) {
        UserNidInfo dbEntity = userNidInfoRepository.findByUserId(id);
        UserNidInfoResponse obj = new UserNidInfoResponse();
        if (dbEntity != null) {
            BeanUtils.copyProperties(dbEntity, obj);
        }
        return obj;
    }

    public UserProfileResponse getUserProfileInfo(Long id) {

        Optional<User> user = userRepository.findById(id);

        UserProfileResponse userProfileResponse = new UserProfileResponse();

        if (user.isPresent()) {
            BeanUtils.copyProperties(user.get(), userProfileResponse);
            UserDetail userDetail = userDetailRepository.findByUserId(id);
            if (userDetail != null) {

                UserDetailResponse userDetailResponse = new UserDetailResponse();

                BeanUtils.copyProperties(userDetail, userDetailResponse);

                userProfileResponse.setUserDetails(userDetailResponse);

                if (userDetailResponse.getPresentAddressId() != null) {

                    AddressResponse presentAddress = addressService
                            .getDataById(userDetailResponse.getPresentAddressId());
                    userDetailResponse.setPresentAddress(presentAddress);
                }

                if (userDetailResponse.getPermanentAddressId() != null) {

                    AddressResponse permanentAddress = addressService
                            .getDataById(userDetailResponse.getPermanentAddressId());
                    userDetailResponse.setPermanentAddress(permanentAddress);
                }

                return userProfileResponse;
            }
            return userProfileResponse;
        }
        return userProfileResponse;
    }

    public UserProfileResponse updateUserProfileInfo(Long id, UserProfileRequest request) {

        Optional<User> user = userRepository.findById(id);
        UserProfileResponse userProfileResponse = new UserProfileResponse();

        if (user.isPresent()) {
            User userRequest = user.get();
            // Update User entity fields
            BeanUtils.copyProperties(request, userRequest, "id");

            User updatedData = userRepository.save(userRequest);

            UserFullResponse userResponse = new UserFullResponse();
            BeanUtils.copyProperties(updatedData, userResponse);

            BeanUtils.copyProperties(userResponse, userProfileResponse);

            // Fetch existing UserDetail
            UserDetail userDetail = userDetailRepository.findByUserId(id);

            if (userDetail != null) {
                // Update fields of the existing UserDetail
                UserDetailRequest userDetailRequest = request.getUserDetails();
                if (userDetailRequest != null) {
                    BeanUtils.copyProperties(userDetailRequest, userDetail, "id", "userId");

                    // Ensure foreign key (userId) is preserved
                    userDetail.setUserId(id);

                    UserDetail updatedUserDetails = userDetailRepository.save(userDetail);

                    // Map updated UserDetail to response
                    UserDetailResponse userDetailResponse = new UserDetailResponse();
                    BeanUtils.copyProperties(updatedUserDetails, userDetailResponse);

                    if (userDetailResponse.getPresentAddressId() != null) {

                        AddressRequest presentAddressRequest = userDetailRequest.getPresentAddress();

                        addressService.updateData(userDetailResponse.getPresentAddressId(), presentAddressRequest);
                    }

                    if (userDetailResponse.getPermanentAddressId() != null) {

                        AddressRequest permanentAddressRequest = userDetailRequest.getPermanentAddress();

                        addressService.updateData(userDetailResponse.getPermanentAddressId(), permanentAddressRequest);
                    }

                    userProfileResponse.setUserDetails(userDetailResponse);
                }
            }
        }

        return userProfileResponse;
    }

    public ResponseEntity<?> uploadUserPhoto(MultipartFile attachment, Long userId) {

        log.info("userId ========== {}", userId);
        UserDetail userDetail = userDetailRepository.findByUserId(userId);

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User Detail Not Found");
        }

        MediaRequest mediaRequest = new MediaRequest();

        mediaRequest.setAttachmentFile(attachment);

        MediaResponse mediaResponse = mediaService.uploadMedia(mediaRequest);

        if (user.get().getPhotoMediaId() != null) {
            mediaService.deleteMediaById(user.get().getPhotoMediaId());
        }

        user.get().setPhotoMediaId(mediaResponse.getId());

        User updatedUser = userRepository.save(user.get());

        return ResponseEntity.ok(updatedUser);

//        log.info("userDetail ========== {}", userDetail);

//        if (userDetail != null) {
//
//            MediaRequest mediaRequest = new MediaRequest();
//
//            mediaRequest.setAttachmentFile(attachment);
//
//            MediaResponse mediaResponse = mediaService.uploadMedia(mediaRequest);
//
//            if (userDetail.getPhotoMediaId() != null) {
//                mediaService.deleteMediaById(userDetail.getPhotoMediaId());
//            }
//
//            userDetail.setPhotoMediaId(mediaResponse.getId());
//
//            userDetailRepository.save(userDetail);
//
//            return ResponseEntity.ok(userDetail);
//        } else {
//            throw new EntityNotFoundException("User Detail Not Found");
//        }
    }

    public ResponseEntity<?> getUserPhoto(Long userId) {

//        UserDetail userDetail = userDetailRepository.findByUserId(userId);

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty() || user.get().getPhotoMediaId() == null) {
            return null;
        }

        return mediaService.getMediaById(user.get().getPhotoMediaId());

//        if (userDetail != null) {
//
//            if (userDetail.getPhotoMediaId() == null) {
//                return null;
//            }
//
//            return mediaService.getMediaById(userDetail.getPhotoMediaId());
//
//        } else {
//            return null;
////            throw new EntityNotFoundException("User Detail Not Found");
//        }
    }

}
