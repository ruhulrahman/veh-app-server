package com.ibas.brta.vehims.configurations.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.payload.request.DocumentTypeRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.DocumentTypeResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.DocumentTypeService;
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
public class DocumentTypeController {

    @Autowired
    DocumentTypeService documentTypeService;

    @PostMapping("/v1/admin/configurations/document-type/create")
    public ResponseEntity<?> createCountryV1(@Valid @RequestBody DocumentTypeRequest request) {
        DocumentTypeResponse saveData = documentTypeService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/document-type/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/document-type/update/{id}")
    public ResponseEntity<?> updateStatusGroup(
            @Valid @PathVariable Long id,
            @RequestBody DocumentTypeRequest request) {

        DocumentTypeResponse updatedData = documentTypeService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/document-type/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/document-type/delete/{id}")
    public ResponseEntity<?> deleteStatusGroup(@PathVariable Long id) {
        documentTypeService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/document-type/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<DocumentTypeResponse> responseData = documentTypeService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID

    @GetMapping("/v1/admin/configurations/document-type/{id}")
    public ResponseEntity<?> getStatusGroupById(@PathVariable Long id) {
        DocumentTypeResponse response = documentTypeService.getDataById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/admin/configurations/document-type/active-list")
    public ResponseEntity<?> getActiveList() {
        return ResponseEntity.ok(documentTypeService.getActiveList());
    }

}
