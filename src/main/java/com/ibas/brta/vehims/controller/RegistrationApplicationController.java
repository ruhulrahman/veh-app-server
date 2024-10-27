package com.ibas.brta.vehims.controller;

import com.ibas.brta.vehims.payload.request.ApplicationFilterRequest;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.projection.RegistrationApplications;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.service.RegistrationApplicationService;
import com.ibas.brta.vehims.util.AppConstants;
import com.ibas.brta.vehims.util.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.List;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/reg/applications")
public class RegistrationApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationApplicationController.class);

    @Autowired
    private RegistrationApplicationService registrationApplicationService;

    @PostMapping("/v1/vehicles")
    public ResponseEntity<?> searchVehicleRegistrationApplicationsV1(@AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestBody ApplicationFilterRequest filters) {

        PagedResponse<RegistrationApplicationResponse> applications = registrationApplicationService
                .searchVehRegApplications(page, size, filters.getServiceRequestNo(), filters.getChassisNumber(),
                        filters.getEngineNumber(), filters.getNid(), filters.getMobile(), filters.getApplicationDate());
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/v1/vehicles/auth-user/registration-application")
    public ResponseEntity<?> getAuthUserVehicleRegistrationApplicationsV1(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestBody ApplicationFilterRequest filters) {

        Gson gson = new Gson();

        PagedResponse<RegistrationApplicationResponse> applications = registrationApplicationService
                .getAuthUserVehicleRegistrationApplicationsV1(page, size, filters.getServiceRequestNo(),
                        filters.getChassisNumber(),
                        filters.getEngineNumber(), filters.getNid(), filters.getMobile(), filters.getApplicationDate(),
                        currentUser.getId());
        return ResponseEntity.ok(applications);
    }

}
