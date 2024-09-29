package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.VehicleRouteRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.VehicleRouteResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.VehicleRouteService;
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
public class VehicleRouteController {

    @Autowired
    VehicleRouteService vehicleRouteService;

    @PostMapping("/v1/admin/configurations/vehicle-route/create")
    public ResponseEntity<?> createData(@Valid @RequestBody VehicleRouteRequest request) {
        VehicleRouteResponse saveData = vehicleRouteService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-route/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-route/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody VehicleRouteRequest request) {

        VehicleRouteResponse updatedData = vehicleRouteService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/vehicle-route/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-route/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        vehicleRouteService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/vehicle-route/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Long routePermitTypeId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<VehicleRouteResponse> responseData = vehicleRouteService.findAllBySearch(nameEn,
                routePermitTypeId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-route/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        VehicleRouteResponse response = vehicleRouteService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
