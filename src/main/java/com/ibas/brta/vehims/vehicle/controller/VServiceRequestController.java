package com.ibas.brta.vehims.vehicle.controller;

import com.ibas.brta.vehims.vehicle.payload.request.VServiceRequestCreateRequest;
import com.ibas.brta.vehims.vehicle.payload.response.VServiceRequestResponse;
import com.ibas.brta.vehims.vehicle.service.VServiceRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/")
public class VServiceRequestController {
    @Autowired
    VServiceRequestService vServiceRequestService;

    @PostMapping("/v1/vservice-request/update-inpection-by-authority")
    public ResponseEntity<?> updateInpectionByAuthority(@RequestBody VServiceRequestCreateRequest request) {
        VServiceRequestResponse vServiceRequestResponse = vServiceRequestService.updateInpectionByAuthority(request);
        return ResponseEntity.ok(vServiceRequestResponse);
    }

    @PostMapping("/v1/vservice-request/update-inpection-by-inspector")
    public ResponseEntity<?> updateInpectionByInspector(@RequestBody VServiceRequestCreateRequest request) {
        vServiceRequestService.updateInpectionByInspector(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/vservice-request/update-revenue-by-authority")
    public ResponseEntity<?> updateRevenueByAuthority(@RequestBody VServiceRequestCreateRequest request) {
        VServiceRequestResponse vServiceRequestResponse = vServiceRequestService.updateRevenueByAuthority(request);
        return ResponseEntity.ok(vServiceRequestResponse);
    }

    @PostMapping("/v1/vservice-request/update-revenue-by-revenue-checker")
    public ResponseEntity<?> updateRevenueByRevenueChecker(@RequestBody VServiceRequestCreateRequest request) {
        vServiceRequestService.updateRevenueByRevenueChecker(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/vservice-request/update-approval-by-authority")
    public ResponseEntity<?> updateApprovalByAuthority(@RequestBody VServiceRequestCreateRequest request) {
        VServiceRequestResponse vServiceRequestResponse = vServiceRequestService.updateApprovalByAuthority(request);
        return ResponseEntity.ok(vServiceRequestResponse);
    }
}
