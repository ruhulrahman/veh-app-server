package com.ibas.brta.vehims.controller;

import com.ibas.brta.vehims.model.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.VehicleTypeDTO;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.service.VehicleTypeService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleTypeController {

    @Autowired
    VehicleTypeService vehicleTypeService;

    // Create a new item
    @PostMapping("/v1/admin/configurations/vehicle-type/create")
    public ResponseEntity<?> createData(@Valid @RequestBody VehicleTypeDTO vehicleTypeDTO) {
        VehicleType saveData = vehicleTypeService.createData(vehicleTypeDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-type/create")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-type/update/{id}")
    public ResponseEntity<?> updateData(
            @Valid @PathVariable Long id,
            @RequestBody VehicleTypeDTO vehicleTypeDTO) {

        VehicleType updatedData = vehicleTypeService.updateData(id, vehicleTypeDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-type/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-type/delete/{id}")
    public ResponseEntity<?> deleteDataById(@PathVariable Long id) {
        vehicleTypeService.deleteDataById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/vehicle-type/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<VehicleTypeResponse> responseData = vehicleTypeService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-type/{id}")
    public ResponseEntity<VehicleTypeResponse> getDataById(@PathVariable Long id) {
        VehicleTypeResponse vehicleType = vehicleTypeService.getDataById(id);
        return ResponseEntity.ok(vehicleType);
    }
}
