package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.NotificationTemplateRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.NotificationTemplateResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.NotificationTemplateService;
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
public class NotificationTemplateController {

    @Autowired
    NotificationTemplateService notificationTemplateService;

    @PostMapping("/v1/admin/configurations/notification-template/create")
    public ResponseEntity<?> createData(@Valid @RequestBody NotificationTemplateRequest request) {
        NotificationTemplateResponse saveData = notificationTemplateService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/notification-template/")
                .buildAndExpand(saveData.getTitleEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getTitleEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/notification-template/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody NotificationTemplateRequest request) {

        NotificationTemplateResponse updatedData = notificationTemplateService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/notification-template/updated")
                .buildAndExpand(updatedData.getTitleEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getTitleEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/notification-template/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        notificationTemplateService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/notification-template/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String titleEn,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<NotificationTemplateResponse> responseData = notificationTemplateService.findAllBySearch(titleEn,
                serviceId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/notification-template/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        NotificationTemplateResponse response = notificationTemplateService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
