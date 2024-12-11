package com.ibas.brta.vehims.userManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.userManagement.model.NidModel;
import com.ibas.brta.vehims.userManagement.payload.request.NidSearchRequest;
import com.ibas.brta.vehims.userManagement.service.NidService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/nid")
public class NidController {

    @Autowired
    private NidService nidService;

    @PostMapping("/v1/get-data-from-nid-service")
    public ResponseEntity<?> getDataFromNidServerV1(@Valid @RequestBody NidSearchRequest request) {

        boolean nidExist = nidService.checkNidExist(request.getNid(), request.getDob());
        if (nidExist) {
            return ResponseEntity.badRequest().body(ApiResponse.failure("NID number already exists", null));
        } else {
            NidModel nidModel = nidService.getPersonDetailsFromNIDTest(request.getNid(), request.getDob());
            return ResponseEntity.ok(ApiResponse.success("nid data", nidModel));
        }

    }

}
