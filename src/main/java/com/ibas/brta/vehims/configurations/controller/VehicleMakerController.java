package com.ibas.brta.vehims.configurations.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.payload.request.VehicleMakerRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleMakerResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.VehicleMakerService;
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
public class VehicleMakerController {

    @Autowired
    VehicleMakerService vehicleMakerService;

    @PostMapping("/v1/admin/configurations/vehicle-maker/create")
    public ResponseEntity<?> createData(@Valid @RequestBody VehicleMakerRequest request) {
        VehicleMakerResponse saveData = vehicleMakerService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-maker/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-maker/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody VehicleMakerRequest request) {

        VehicleMakerResponse updatedData = vehicleMakerService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-maker/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-maker/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        vehicleMakerService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/vehicle-maker/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Long countryId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<VehicleMakerResponse> responseData = vehicleMakerService.findAllBySearch(nameEn,
                countryId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-maker/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        VehicleMakerResponse response = vehicleMakerService.getDataById(id);
        return ResponseEntity.ok(response);
    }

    // Active List
    @GetMapping("/v1/admin/configurations/vehicle-maker/active-list")
    public ResponseEntity<?> getActiveList() {
        List<?> response = vehicleMakerService.getActiveList();
        return ResponseEntity.ok(response);
    }

}
