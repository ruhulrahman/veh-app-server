package com.ibas.brta.vehims.configurations.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.configurations.payload.request.ServiceDocumentMapRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.ServiceDocumentMapResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.ServiceDocumentMapService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

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
public class ServiceDocumentMapController {

    @Autowired
    ServiceDocumentMapService serviceDocumentMapService;

    @PostMapping("/v1/admin/configurations/service-document-map/create")
    public ResponseEntity<?> createData(@Valid @RequestBody ServiceDocumentMapRequest request) {
        ServiceDocumentMapResponse saveData = serviceDocumentMapService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/service-document-map/")
                .buildAndExpand("").toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success("Data saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/service-document-map/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody ServiceDocumentMapRequest request) {

        ServiceDocumentMapResponse updatedData = serviceDocumentMapService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/service-document-map/updated")
                .buildAndExpand("").toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success("Data updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/service-document-map/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        serviceDocumentMapService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/service-document-map/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) Long documentTypeId,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<ServiceDocumentMapResponse> responseData = serviceDocumentMapService.findAllBySearch(
                documentTypeId,
                serviceId,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/service-document-map/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        ServiceDocumentMapResponse response = serviceDocumentMapService.getDataById(id);
        return ResponseEntity.ok(response);
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/service-document-map-service-id/{serviceId}")
    public ResponseEntity<?> getServiceDocumentMapByServiceId(@PathVariable Long serviceId) {
        List<ServiceDocumentMapResponse> response = serviceDocumentMapService
                .getServiceDocumentMapByServiceId(serviceId);
        return ResponseEntity.ok(response);
    }

}
