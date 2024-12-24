package com.ibas.brta.vehims.configurations.controller;

import com.ibas.brta.vehims.configurations.model.VehicleRegistrationMark;
import com.ibas.brta.vehims.configurations.model.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.payload.request.VehicleRegistrationMarkRequest;
import com.ibas.brta.vehims.configurations.payload.request.VehicleTypeRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleRegistrationMarkResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.configurations.service.VehicleRegistrationMarkService;
import com.ibas.brta.vehims.configurations.service.VehicleTypeService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class VehicleRegistrationMarkController {

    @Autowired
    VehicleRegistrationMarkService vehicleRegistrationMarkService;

    // Create a new item
    @PostMapping("/v1/admin/configurations/vehicle-registration-mark/create")
    public ResponseEntity<?> createData(@Valid @RequestBody VehicleRegistrationMarkRequest request) {

        VehicleRegistrationMark saveData = vehicleRegistrationMarkService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-registration-mark/create")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-registration-mark/update/{id}")
    public ResponseEntity<?> updateData(
            @Valid @PathVariable Long id,
            @RequestBody VehicleRegistrationMarkRequest request) {

        VehicleRegistrationMark updatedData = vehicleRegistrationMarkService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-registration-mark/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-registration-mark/delete/{id}")
    public ResponseEntity<?> deleteDataById(@PathVariable Long id) {
        vehicleRegistrationMarkService.deleteDataById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/vehicle-registration-mark/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<VehicleRegistrationMarkResponse> responseData = vehicleRegistrationMarkService.findAllBySearch(
                nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-registration-mark/{id}")
    public ResponseEntity<VehicleRegistrationMarkResponse> getDataById(@PathVariable Long id) {
        VehicleRegistrationMarkResponse vehicleType = vehicleRegistrationMarkService.getDataById(id);
        return ResponseEntity.ok(vehicleType);
    }
}
