package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.VehicleBrandRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.VehicleBrandResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.VehicleBrandService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;
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
public class VehicleBrandController {

    @Autowired
    VehicleBrandService vehicleBrandService;

    @PostMapping("/v1/admin/configurations/vehicle-brand/create")
    public ResponseEntity<?> createData(@Valid @RequestBody VehicleBrandRequest request) {
        VehicleBrandResponse saveData = vehicleBrandService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-brand/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-brand/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody VehicleBrandRequest request) {

        VehicleBrandResponse updatedData = vehicleBrandService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-brand/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-brand/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        vehicleBrandService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/vehicle-brand/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Long makerId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<VehicleBrandResponse> responseData = vehicleBrandService.findAllBySearch(nameEn,
                makerId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-brand/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        VehicleBrandResponse response = vehicleBrandService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
