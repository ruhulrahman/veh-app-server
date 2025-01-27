package com.ibas.brta.vehims.userManagement.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.payload.request.ApplicantUserRequest;
import com.ibas.brta.vehims.userManagement.payload.request.NidSearchRequest;
import com.ibas.brta.vehims.userManagement.payload.request.UserRegistrationRequest;
import com.ibas.brta.vehims.userManagement.payload.response.UserFullResponse;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import com.ibas.brta.vehims.userManagement.service.InMemoryOtpService;
import com.ibas.brta.vehims.userManagement.service.OtpService;
import com.ibas.brta.vehims.userManagement.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private InMemoryOtpService inMemoryOtpService;

    @Autowired
    UserService userService;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserNidInfoRepository userNidInfoRepository;

    // Endpoint to generate OTP In Memory Cache
    @PostMapping("/v1/send-user-registrtaion-otp")
    public ResponseEntity<?> generateOtpInMemoryCacheV1(@Valid @RequestBody UserRegistrationRequest request) {
        String otp = inMemoryOtpService.generateOtp(request.getMobile());
        // Typically, you'd send the OTP to the user here (e.g., via SMS or email)
        // OTP has been generated and sent to the user

        Optional<User> user = userRepository.findByUsernameOrMobileOrEmail(request.getUsername(), request.getMobile(),
                request.getEmail());

        if (user.isPresent()) {
            return ResponseEntity.ok(ApiResponse.failure("Username or mobile or email already exists", null));
        } else {
            return ResponseEntity.ok(ApiResponse.success("OTP has been generated and sent to the user", otp));
        }

    }

    // Endpoint to verify OTP In Memory Cache
    @Transactional
    @PostMapping("/v1/verify-user-registrtaion-otp")
    public ResponseEntity<?> verifyOtpInMemoryCacheV1(@Valid @RequestBody UserRegistrationRequest request) {

        boolean isValid = inMemoryOtpService.verifyOtp(request.getMobile(), request.getOtp());
        String verifyingMsg = isValid ? "OTP verified successfully." : "Invalid OTP or OTP expired.";

        if (isValid) {
            // User registration logic goes here
            ApplicantUserRequest userRequest = new ApplicantUserRequest();
            BeanUtils.copyProperties(request, userRequest);
            userRequest.setNameBn(request.getName());
            StatusProjection userTypeStatus = commonRepository.getStatusByStatusCodeOrId("applicant");
            if (userTypeStatus != null) {
                userRequest.setUserTypeId(userTypeStatus.getId());
            }

            UserFullResponse userResponse = userService.createApplicantUserData(userRequest);

            log.info("userResponse.getId() : {}", userResponse.getId());

            if (userResponse != null) {
                // Save user nid info
                UserNidInfo userNidInfoRequest = new UserNidInfo();
                BeanUtils.copyProperties(request, userNidInfoRequest);

                userNidInfoRequest.setUserId(userResponse.getId());
                userNidInfoRequest.setNidNumber(request.getNid());
                userNidInfoRequest.setNameBn(request.getName());
                userNidInfoRequest.setFatherOrHusbandNameBn(request.getFather());
                userNidInfoRequest.setMotherNameBn(request.getMother());
                userNidInfoRequest.setAddressBn(request.getPresentAddress());
                if (request.getGender() != null) {
                    if (request.getGender().equals("male")) {
                        userNidInfoRequest.setGenderId(1L);
                    } else if (request.getGender().equals("female")) {
                        userNidInfoRequest.setGenderId(2L);
                    } else {
                        userNidInfoRequest.setGenderId(4L);
                    }
                }

                if (request.getDob() != null) {
                    userNidInfoRequest.setDob(LocalDate.parse(request.getDob()));
                }

                // userNidInfoRequest.setGenderId(null);

                userNidInfoRepository.save(userNidInfoRequest);
            }
        }

        return ResponseEntity.ok(ApiResponse.success(verifyingMsg, isValid));
    }

    // @PostMapping("/send")
    // public String sendOtp(@RequestParam String phoneNumber) {
    // return otpService.generateOtp(phoneNumber);
    // }

    // @PostMapping("/v1/send-user-registrtaion-otp")
    // public ResponseEntity<?> sendUserRegistrationOtpV1(@Valid @RequestBody
    // UserRegistrationRequest request) {

    // String otp = otpService.generateOtp(request.getMobile());

    // return ResponseEntity.ok(ApiResponse.success("Fetched list", otp));
    // }

    // @PostMapping("/v1/verify-user-registrtaion-otp")
    // public boolean verifyOtp(@RequestParam String phoneNumber, @RequestParam
    // String otp) {

    // boolean isValid = otpService.validateOtp(phoneNumber, otp);

    // if (isValid) {
    // // User registration logic goes here
    // } else {
    // // Handle invalid OTP
    // }

    // return isValid;
    // }

}
