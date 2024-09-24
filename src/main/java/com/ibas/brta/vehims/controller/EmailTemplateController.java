package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.EmailTemplateRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.EmailTemplateResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.EmailTemplateService;
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
public class EmailTemplateController {

    @Autowired
    EmailTemplateService emailTemplateService;

    @PostMapping("/v1/admin/configurations/email-template/create")
    public ResponseEntity<?> createData(@Valid @RequestBody EmailTemplateRequest request) {
        EmailTemplateResponse saveData = emailTemplateService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/email-template/")
                .buildAndExpand(saveData.getTemplateName()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getTemplateName() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/email-template/update/{id}")
    public ResponseEntity<?> updateData(
            @PathVariable Long id,
            @RequestBody EmailTemplateRequest request) {

        EmailTemplateResponse updatedData = emailTemplateService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/email-template/updated")
                .buildAndExpand(updatedData.getTemplateName()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getTemplateName() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/email-template/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        emailTemplateService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/email-template/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String templateName,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<EmailTemplateResponse> responseData = emailTemplateService.findAllBySearch(templateName,
                serviceId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/email-template/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        EmailTemplateResponse response = emailTemplateService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
