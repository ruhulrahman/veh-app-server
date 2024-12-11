package com.ibas.brta.vehims.configurations.controller;

import com.ibas.brta.vehims.configurations.model.BloodGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.payload.request.BloodGroupDTO;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.BloodGroupResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.BloodGroupService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class BloodGroupController {

    @Autowired
    BloodGroupService bloodGroupService;

    // Create a new item
    @PostMapping("/v1/admin/configurations/blood/create")
    public ResponseEntity<?> createData(@Valid @RequestBody BloodGroupDTO bloodGroupDTO) {
        BloodGroup saveData = bloodGroupService.createData(bloodGroupDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/blood/create")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/blood/update/{id}")
    public ResponseEntity<?> updateData(
            @Valid @PathVariable Long id,
            @RequestBody BloodGroupDTO bloodGroupDTO) {

        BloodGroup updatedData = bloodGroupService.updateData(id, bloodGroupDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/blood/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/blood/delete/{id}")
    public ResponseEntity<?> deleteDataById(@PathVariable Long id) {
        bloodGroupService.deleteDataById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/blood/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<BloodGroupResponse> responseData = bloodGroupService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/blood/{id}")
    public ResponseEntity<BloodGroupResponse> getDataById(@PathVariable Long id) {
        BloodGroupResponse response = bloodGroupService.getDataById(id);
        return ResponseEntity.ok(response);
    }
}