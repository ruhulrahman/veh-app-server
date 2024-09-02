package com.ibas.brta.vehims.controller;

import com.ibas.brta.vehims.exception.AppException;
import com.ibas.brta.vehims.exception.ErrorDetails;
import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.model.rbac.RoleName;
import com.ibas.brta.vehims.payload.request.SignupRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.service.DesignationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/")
public class DesignationController {
    @Autowired
    DesignationService designationService;

    @PostMapping("/v1/designations")
    public ResponseEntity<?> createDesignationV1(@Valid @RequestBody Designation designation) {

        Designation _designation = designationService.saveDesignation(designation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/designations/")
                .buildAndExpand(_designation.getNameInEng()).toUri();

        return ResponseEntity.created(location).body(ApiResponse.success(_designation.getNameInEng()+" created.", _designation));
    }
}
