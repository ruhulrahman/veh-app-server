package com.ibas.brta.vehims.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.OfficeJurisdictionRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.OfficeJurisdictionResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.OfficeJurisdictionService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class OfficeJurisdictionController {

    @Autowired
    OfficeJurisdictionService officeJurisdictionService;

    @PostMapping("/v1/admin/configurations/office-jurisdiction/create")
    public ResponseEntity<?> createData(@Valid @RequestBody OfficeJurisdictionRequest request) {
        OfficeJurisdictionResponse saveData = officeJurisdictionService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/office-jurisdiction/")
                .buildAndExpand(saveData.getOrgNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getOrgNameEn() + " Office jursidiction saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/office-jurisdiction/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody OfficeJurisdictionRequest request) {

        OfficeJurisdictionResponse updatedData = officeJurisdictionService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/office-jurisdiction/updated")
                .buildAndExpand(updatedData.getOrgNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getOrgNameEn() + " Office jursidiction updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/office-jurisdiction/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        officeJurisdictionService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/office-jurisdiction/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) Long orgId,
            @RequestParam(required = false) Long thanaId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<OfficeJurisdictionResponse> responseData = officeJurisdictionService.findAllBySearch(
                orgId,
                thanaId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/office-jurisdiction/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        OfficeJurisdictionResponse response = officeJurisdictionService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
