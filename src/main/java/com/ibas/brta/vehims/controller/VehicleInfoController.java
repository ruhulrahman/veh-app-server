package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.PermissionRequest;
import com.ibas.brta.vehims.payload.request.VehicleRegPage1Request;
import com.ibas.brta.vehims.payload.request.VehicleRegPage2Request;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.PermissionResponse;
import com.ibas.brta.vehims.payload.response.VehicleInfoResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.PermissionService;
import com.ibas.brta.vehims.service.VehicleInfoService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/")
public class VehicleInfoController {

        @Autowired
        VehicleInfoService vehicleInfoService;

        @PostMapping("/v1/applicant/vehicle/registration-application-page1")
        public ResponseEntity<?> storeVehicleRegPage1(@Valid @RequestBody VehicleRegPage1Request request) {
                VehicleInfoResponse saveData = vehicleInfoService.storeVehicleRegPage1(request);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/")
                                .buildAndExpand("").toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success("Page 1 data saved.", saveData));
        }

        @PostMapping("/v1/applicant/vehicle/registration-application-page2")
        public ResponseEntity<?> storeVehicleRegPage2(@Valid @RequestBody VehicleRegPage2Request request) {
                VehicleInfoResponse saveData = vehicleInfoService.storeVehicleRegPage2(request);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/")
                                .buildAndExpand("").toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success("Page 2 data saved.", saveData));
        }

        @GetMapping("/v1/applicant/vehicle/registration-application/list")
        public PagedResponse<?> findListWithPaginationBySearch(
                        @RequestParam(required = false) String chassisNumber,
                        @RequestParam(required = false) String engineNumber,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

                PagedResponse<VehicleInfoResponse> responseData = vehicleInfoService.findAllBySearch(
                                chassisNumber,
                                engineNumber,
                                page,
                                size);

                return responseData;
        }

        // Get a single item by ID
        @GetMapping("/v1/applicant/vehicle/{id}")
        public ResponseEntity<?> getDataById(@PathVariable Long id) {
                VehicleInfoResponse response = vehicleInfoService.getDataById(id);
                return ResponseEntity.ok(response);
        }

}
