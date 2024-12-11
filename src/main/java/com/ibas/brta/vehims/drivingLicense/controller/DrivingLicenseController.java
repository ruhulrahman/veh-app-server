package com.ibas.brta.vehims.drivingLicense.controller;

import com.ibas.brta.vehims.drivingLicense.payload.response.DLApplicationResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceRequestDetailsResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceRequestResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.drivingLicense.payload.response.LearnerDetailsResponse;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLApplicationPage1Request;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLApplicationPage2Request;
import com.ibas.brta.vehims.drivingLicense.payload.request.GetDrivingLicenseApplicationRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.security.CurrentUser;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.drivingLicense.service.DrivingLicenseService;
import com.ibas.brta.vehims.drivingLicense.service.LearnerLicenseService;
import com.ibas.brta.vehims.util.AppConstants;
import com.ibas.brta.vehims.util.Utils;
import com.ibas.brta.vehims.vehicle.payload.response.VServiceRequestResponse;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
// @RequestMapping("/api/reg/applications")
@RequestMapping("/api")
public class DrivingLicenseController {

    @Autowired
    private DrivingLicenseService drivingLicenseService;

    @Autowired
    private LearnerLicenseService learnerLicenseService;

    // @PostMapping("/driving-license/v1/driving-license")
    @PostMapping("/driving-license/v1/application-list")
    public ResponseEntity<?> searchVehicleRegistrationApplicationsV1(@AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Valid @RequestBody GetDrivingLicenseApplicationRequest filters) {

        PagedResponse<DrivingLicenseApplicationDto> applications = drivingLicenseService
                .searchDrivingLicenseApplications(page, size, filters.getServiceRequestNo(), filters.getNid(),
                        filters.getLearnerNo(), filters.getMobile(), filters.getApplicationDate(), null);
        return ResponseEntity.ok(applications);
    }

    // @PostMapping("/driving-license/v1/driving-license")
    @PostMapping("/driving-license/v1/application-list-for-applicant")
    public ResponseEntity<?> searchDLApplicationsForApplicantV1(@AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Valid @RequestBody GetDrivingLicenseApplicationRequest filters) {

        PagedResponse<DrivingLicenseApplicationDto> applications = drivingLicenseService
                .searchDrivingLicenseApplicationsForApplicant(page, size, filters.getServiceRequestNo(),
                        filters.getNid(),
                        filters.getLearnerNo(), filters.getMobile(), filters.getApplicationDate(), null);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/driving-license/v1/application-page1")
    public ResponseEntity<?> storeDLApplicationPage1(
            @Valid @RequestBody DLApplicationPage1Request request) {

        DLApplicationResponse dlServiceRequestResponse = drivingLicenseService.storeDLApplicationPage1(request);
        return ResponseEntity.ok(ApiResponse.success("Page 1 data saved.", dlServiceRequestResponse));
    }

    @PostMapping("/driving-license/v1/application-page2")
    public ResponseEntity<?> storeDLApplicationPage2(
            @Valid @RequestBody DLApplicationPage2Request request) {

        DLApplicationResponse dlServiceRequestResponse = drivingLicenseService.storeDLApplicationPage2(request);
        return ResponseEntity.ok(ApiResponse.success("Page 2 data saved.", dlServiceRequestResponse));
    }

    @GetMapping("/driving-license/v1/get-application-details-by-service-request-no/{serviceRequestNo}")
    public ResponseEntity<?> getDLServiceRequestByServiceRequestNo(@PathVariable String serviceRequestNo) {

        DLApplicationResponse dlServiceRequestResponse = drivingLicenseService
                .getDLServiceRequestByServiceRequestNo(serviceRequestNo);
        return ResponseEntity.ok(dlServiceRequestResponse);
    }

    @GetMapping("/driving-license/v1/get-application-details-by-user")
    public ResponseEntity<?> getServiceRequestByAuthUser() {

        Boolean isServiceRequest = drivingLicenseService
                .checkDLServiceRequestAvailability();
        Map<String, Object> customArray = new HashMap<>();
        customArray.put("isServiceRequest", isServiceRequest);

        if (!isServiceRequest) {
            DLServiceRequestDetailsResponse dlServiceRequestResponse = drivingLicenseService
                    .getServiceRequestByApplicantIdAndApplicationStatusIds();

            customArray.put("dlServiceRequestResponse", dlServiceRequestResponse);
        }

        return ResponseEntity.ok(customArray);
    }

    @GetMapping("/driving-license/v1/get-learner-by-service-request-no")
    public ResponseEntity<?> getLearnerByServiceRequestNo1(@RequestParam(required = true) String serviceRequestNo) {

        LearnerDetailsResponse learnerDetails = learnerLicenseService
                .getLearnerDetailsByServiceRequestNo(serviceRequestNo);

        return ResponseEntity.ok(learnerDetails);
    }

}
