package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.GenderRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.GenderResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.GenderService;
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
public class GenderController {

    @Autowired
    GenderService genderService;

    @PostMapping("/v1/admin/configurations/gender/create")
    public ResponseEntity<?> createCountryV1(@Valid @RequestBody GenderRequest genderRequest) {
        GenderResponse saveData = genderService.createData(genderRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/gender/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/gender/update/{id}")
    public ResponseEntity<?> updateData(
            @Valid @PathVariable Long id,
            @RequestBody GenderRequest genderRequest) {

        GenderResponse updatedData = genderService.updateData(id, genderRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/gender/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/gender/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        genderService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/gender/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<GenderResponse> responseData = genderService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID

    @GetMapping("/v1/admin/configurations/gender/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        GenderResponse response = genderService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
