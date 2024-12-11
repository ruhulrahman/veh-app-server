package com.ibas.brta.vehims.configurations.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.payload.request.FiscalYearRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.FiscalYearResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.FiscalYearService;
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
public class FiscalYearController {

    @Autowired
    FiscalYearService fiscalYearService;

    @PostMapping("/v1/admin/configurations/fiscal-year/create")
    public ResponseEntity<?> createData(@Valid @RequestBody FiscalYearRequest request) {
        FiscalYearResponse saveData = fiscalYearService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/fiscal-year/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/fiscal-year/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody FiscalYearRequest request) {

        FiscalYearResponse updatedData = fiscalYearService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/fiscal-year/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/fiscal-year/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        fiscalYearService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/fiscal-year/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<FiscalYearResponse> responseData = fiscalYearService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/fiscal-year/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        FiscalYearResponse response = fiscalYearService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
