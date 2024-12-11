package com.ibas.brta.vehims.configurations.controller;

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

import com.ibas.brta.vehims.configurations.payload.request.OrganizationRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.OrganizationResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.OrganizationService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @PostMapping("/v1/admin/configurations/organization/create")
    public ResponseEntity<?> createData(@Valid @RequestBody OrganizationRequest request) {
        OrganizationResponse saveData = organizationService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/organization/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/organization/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody OrganizationRequest request) {

        OrganizationResponse updatedData = organizationService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/organization/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/organization/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        organizationService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/organization/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Long officeTypeId,
            @RequestParam(required = false) Long divisionId,
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<OrganizationResponse> responseData = organizationService.findAllBySearch(
                nameEn,
                officeTypeId,
                divisionId,
                districtId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/organization/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        OrganizationResponse response = organizationService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
