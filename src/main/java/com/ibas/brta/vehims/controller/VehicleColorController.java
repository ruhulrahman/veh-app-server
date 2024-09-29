package com.ibas.brta.vehims.controller;

import com.ibas.brta.vehims.model.VehicleColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.VehicleColorDTO;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.VehicleColorResponse;
import com.ibas.brta.vehims.service.VehicleColorService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class VehicleColorController {

    @Autowired
    VehicleColorService vehicleColorService;

    // Create a new item
    @PostMapping("/v1/admin/configurations/vehicle-color/create")
    public ResponseEntity<?> createData(@Valid @RequestBody VehicleColorDTO vehicleColorDTO) {
        VehicleColor saveData = vehicleColorService.createData(vehicleColorDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-color/create")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-color/update/{id}")
    public ResponseEntity<?> updateData(
            @Valid @PathVariable Long id,
            @RequestBody VehicleColorDTO vehicleColorDTO) {

        VehicleColor updatedData = vehicleColorService.updateData(id, vehicleColorDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-color/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-color/delete/{id}")
    public ResponseEntity<?> deleteDataById(@PathVariable Long id) {
        vehicleColorService.deleteDataById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/vehicle-color/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<VehicleColorResponse> responseData = vehicleColorService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-color/{id}")
    public ResponseEntity<VehicleColor> getDataById(@PathVariable Long id) {
        VehicleColor vehicleColor = vehicleColorService.getDataById(id);
        return ResponseEntity.ok(vehicleColor);
    }
}
