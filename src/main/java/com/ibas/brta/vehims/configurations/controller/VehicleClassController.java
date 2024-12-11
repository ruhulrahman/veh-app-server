package com.ibas.brta.vehims.configurations.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.payload.request.VehicleClassRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleClassResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.VehicleClassService;
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
public class VehicleClassController {

    @Autowired
    VehicleClassService vehicleClassService;

    @PostMapping("/v1/admin/configurations/vehicle-class/create")
    public ResponseEntity<?> createData(@Valid @RequestBody VehicleClassRequest request) {
        VehicleClassResponse saveData = vehicleClassService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-class/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-class/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody VehicleClassRequest request) {

        VehicleClassResponse updatedData = vehicleClassService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-class/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-class/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        vehicleClassService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/vehicle-class/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Long vehicleTypeId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<VehicleClassResponse> responseData = vehicleClassService.findAllBySearch(nameEn,
                vehicleTypeId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-class/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        VehicleClassResponse response = vehicleClassService.getDataById(id);
        return ResponseEntity.ok(response);
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-class/get-vehicle-class-list-by-vehicle-type-id/{vehicleTypeId}")
    public ResponseEntity<?> getVehicleClasses(@PathVariable Long vehicleTypeId) {
        List<VehicleClassResponse> response = vehicleClassService.getVehicleClasses(vehicleTypeId);
        return ResponseEntity.ok(response);
    }

}
