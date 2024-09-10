package com.ibas.brta.vehims.controller;

import com.ibas.brta.vehims.exception.AppException;
import com.ibas.brta.vehims.exception.ErrorDetails;
import com.ibas.brta.vehims.model.Country;
import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.model.rbac.RoleName;
import com.ibas.brta.vehims.payload.request.DesignationRequest;
import com.ibas.brta.vehims.payload.request.SignupRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.DesignationService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class DesignationController {
        @Autowired
        DesignationService designationService;

        private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);

        @PostMapping("/v1/designation/create")
        public ResponseEntity<?> createDesignationV1(@Valid @RequestBody Designation designation) {

                logger.info("designation ==", designation.getCreatedBy());

                designation.setCreatedBy(2L);

                Designation _designation = designationService.saveDesignation(designation);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/designation/create/")
                                .buildAndExpand(_designation.getNameEn()).toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success(_designation.getNameEn() + " created.", _designation));
        }

        // @GetMapping("/v1/designation/list")
        // public ResponseEntity<?> designationList(@RequestParam DesignationRequest
        // request) {
        // Page<Designation> _designations =
        // designationService.findAllDesignationsWithPagination(request);

        // URI location = ServletUriComponentsBuilder
        // .fromCurrentContextPath().path("/designation/list/")
        // .buildAndExpand(_designations).toUri();

        // return ResponseEntity.created(location).body(ApiResponse.success("Fetched
        // list", _designations));
        // }

        // @PreAuthorize("hasAnyAuthority('READ_OP')")
        // @GetMapping("/v1/vehicles/data")
        // public PagedResponse<VehdataResponse> getRecords(
        // @RequestParam(value = "page", defaultValue =
        // AppConstants.DEFAULT_PAGE_NUMBER) int page,
        // @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE)
        // int size,
        // @RequestParam(value = "searchkey", defaultValue =
        // AppConstants.DEFAULT_PARAM_VALUE) String searchkey) {

        // logger.error("Finding vehicle data as paged." + searchkey);

        // PagedResponse<VehdataResponse> vehdataResponse =
        // vehicleService.searchByRegistrationNo(page, size,
        // searchkey.trim().toLowerCase());

        // return vehdataResponse;
        // }

        @GetMapping("/search-designation")
        public ResponseEntity<?> getDesignationBySearch(
                        @RequestParam(required = false) String nameEn,
                        @RequestParam(required = false) Boolean isActive,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

                // return designationService.getDesignationBySearch(nameEn, isActive, page,
                // size);

                PagedResponse<DesignationResponse> responseData = designationService.getDesignationBySearch(nameEn,
                                isActive,
                                page,
                                size);

                // return responseData;
                return ResponseEntity.ok(ApiResponse.success("Fetched list", responseData));
        }

        @GetMapping("/v1/designation/list")
        public ResponseEntity<?> designationList() {
                List<Designation> _designations = designationService.findAllDesignations();

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/designation/list/")
                                .buildAndExpand(_designations).toUri();

                // return ResponseEntity.created(location).body(ApiResponse.success("Fetched
                // list", _designations));
                return ResponseEntity.ok(ApiResponse.success("Fetched list", _designations));
        }

        @GetMapping("/v1/designation/parent-list")
        public ResponseEntity<?> getParentDesignationList() {
                List<Designation> _designations = designationService.getParentDesignationList();

                return ResponseEntity.ok(ApiResponse.success("Fetched list", _designations));
        }

}
