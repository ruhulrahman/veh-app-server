package com.ibas.brta.vehims.vehicle.controller;

import com.ibas.brta.vehims.vehicle.payload.request.ApplicationFilterRequest;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegReportFilterRequest;
import com.ibas.brta.vehims.vehicle.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.vehicle.payload.response.VehRegistrationApplicationDTO;
import com.ibas.brta.vehims.vehicle.service.RegistrationApplicationService;
import com.ibas.brta.vehims.util.AppConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                        filters.getEngineNumber(), filters.getNid(), filters.getMobile(), filters.getApplicationDate(), null);
        return ResponseEntity.ok(applications);
    }


    @PostMapping("/v1/vehicles-for-approval")
    public ResponseEntity<?> searchVehicleRegistrationApplicationsForApprovalV1(@AuthenticationPrincipal UserPrincipal currentUser,
                                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                                                     @RequestBody ApplicationFilterRequest filters) {

        PagedResponse<RegistrationApplicationResponse> applications = registrationApplicationService
                .searchVehRegApplicationsForApproval(page, size, filters.getServiceRequestNo(), filters.getChassisNumber(),
                        filters.getEngineNumber(), filters.getNid(), filters.getMobile(), filters.getApplicationDate(), currentUser.getId());
        return ResponseEntity.ok(applications);
    }


    @PostMapping("/v1/vehicles-for-mvi")
    public ResponseEntity<?> searchVehicleRegistrationApplicationsForMviV1(@AuthenticationPrincipal UserPrincipal currentUser,
                                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                                                     @RequestBody ApplicationFilterRequest filters) {

        filters.setInspectorId(currentUser.getId());
        PagedResponse<VehRegistrationApplicationDTO> applications = registrationApplicationService
                .searchVehRegApplicationsForMvi(page, size, filters);
        return ResponseEntity.ok(applications);
    }


    @PostMapping("/v1/vehicles/auth-user/registration-application")
    public ResponseEntity<?> getAuthUserVehicleRegistrationApplicationsV1(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestBody ApplicationFilterRequest filters) {

        PagedResponse<RegistrationApplicationResponse> applications = registrationApplicationService
                .searchVehRegApplications(page, size, filters.getServiceRequestNo(),
                        filters.getChassisNumber(),
                        filters.getEngineNumber(), filters.getNid(), filters.getMobile(), filters.getApplicationDate(),
                        currentUser.getId());
        return ResponseEntity.ok(applications);
    }

//    @GetMapping("/v1/vehicles/registration-report")
//    public ResponseEntity<?> getVehicleRegistrationReport(
//            @RequestParam(required = false) Long orgId,
//            @RequestParam(required = false) Long vehicleTypeId,
//            @RequestParam(required = false) Date registrationDate) {
//
//        Object response = registrationApplicationService.getVehicleRegistrationReport(orgId, vehicleTypeId, registrationDate);
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/v1/vehicles/registration-report")
    public ResponseEntity<?> getVehicleRegistrationReport(@ModelAttribute VehicleRegReportFilterRequest request) {

        Object response = registrationApplicationService.getVehicleRegistrationReport(request);
        return ResponseEntity.ok(response);
    }

}
