package com.ibas.brta.vehims.controller;

import com.google.gson.Gson;
import com.ibas.brta.vehims.payload.drivingLicense.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.payload.drivingLicense.GetDrivingLicenseApplicationRequest;
import com.ibas.brta.vehims.payload.request.ApplicationFilterRequest;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.service.DrivingLicenseService;
import com.ibas.brta.vehims.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reg/applications")
public class DrivingLicenseController {

    @Autowired
    private DrivingLicenseService drivingLicenseService;

    @PostMapping("/v1/driving-license")
    public ResponseEntity<?> searchVehicleRegistrationApplicationsV1(@AuthenticationPrincipal UserPrincipal currentUser,
                                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                                                     @Valid @RequestBody GetDrivingLicenseApplicationRequest filters) {

        PagedResponse<DrivingLicenseApplicationDto> applications = drivingLicenseService
                .searchDrivingLicenseApplications(page, size, filters.getServiceRequestNo(), filters.getNid(),
                        filters.getLernerNo(), filters.getMobile(), filters.getApplicationDate());
        return ResponseEntity.ok(applications);
    }
}
