package com.ibas.brta.vehims.drivingLicense.controller;

import com.ibas.brta.vehims.drivingLicense.payload.request.DLServiceRequestCreateRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceRequestDetailsResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceRequestResponse;
import com.ibas.brta.vehims.drivingLicense.service.DLServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class DLServiceRequestController {
    @Autowired
    DLServiceRequestService dlServiceRequestService;

    @PostMapping("/v1/dlservice-request/update-mvi-submission")
    public ResponseEntity<?> updateMVISubmission(
            @RequestBody DLServiceRequestCreateRequest dlServiceRequestCreateRequest) {
        DLServiceRequestDetailsResponse dlServiceRequestResponse = dlServiceRequestService
                .updateMVISubmission(dlServiceRequestCreateRequest);
        return ResponseEntity.ok(dlServiceRequestResponse);
    }

    @PostMapping("/v1/dlservice-request/update-approval-by-authority")
    public ResponseEntity<?> updateApprovalByAuthority(
            @RequestBody DLServiceRequestCreateRequest dlServiceRequestCreateRequest) {
        DLServiceRequestDetailsResponse dlServiceRequestResponse = dlServiceRequestService
                .updateApprovalByAuthority(dlServiceRequestCreateRequest);
        return ResponseEntity.ok(dlServiceRequestResponse);
    }
}
