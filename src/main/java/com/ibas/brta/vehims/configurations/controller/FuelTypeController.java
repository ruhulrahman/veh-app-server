package com.ibas.brta.vehims.configurations.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.model.FuelType;
import com.ibas.brta.vehims.configurations.payload.request.FuelTypeDTO;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.FuelTypeResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.FuelTypeService;
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
public class FuelTypeController {

    @Autowired
    FuelTypeService fuelTypeService;

    @PostMapping("/v1/admin/configurations/fuel-type/create")
    public ResponseEntity<?> createCountryV1(@Valid @RequestBody FuelTypeDTO fuelTypeDTO) {
        FuelType saveData = fuelTypeService.createData(fuelTypeDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/fuel-type/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/fuel-type/update/{id}")
    public ResponseEntity<?> updateData(
            @Valid @PathVariable Long id,
            @RequestBody FuelTypeDTO fuelTypeDTO) {

        FuelType updatedData = fuelTypeService.updateData(id, fuelTypeDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/fuel-type/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/fuel-type/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        fuelTypeService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/fuel-type/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<FuelTypeResponse> responseData = fuelTypeService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID

    @GetMapping("/v1/admin/configurations/fuel-type/{id}")
    public ResponseEntity<FuelType> getDataById(@PathVariable Long id) {
        FuelType fuelType = fuelTypeService.getDataById(id);
        return ResponseEntity.ok(fuelType);
    }

}
