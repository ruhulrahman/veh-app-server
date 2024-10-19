package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.model.ServiceEntity;
import com.ibas.brta.vehims.payload.request.ServiceEntityDTO;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.ServiceEntityResponse;
import com.ibas.brta.vehims.service.ServiceEntityService;
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
public class ServiceEntityController {

    @Autowired
    ServiceEntityService serviceEntityService;

    @PostMapping("/v1/admin/configurations/service/create")
    public ResponseEntity<?> createCountryV1(@Valid @RequestBody ServiceEntityDTO serviceEntityDTO) {
        ServiceEntityDTO saveData = serviceEntityService.createData(serviceEntityDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/service/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/service/update/{id}")
    public ResponseEntity<?> updateData(
            @Valid @PathVariable Long id,
            @RequestBody ServiceEntityDTO serviceEntityDTO) {

        ServiceEntityDTO updatedData = serviceEntityService.updateData(id, serviceEntityDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/service/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/service/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        serviceEntityService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/service/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Long parentServiceId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<ServiceEntityResponse> responseData = serviceEntityService.findAllBySearch(nameEn,
                parentServiceId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID

    @GetMapping("/v1/admin/configurations/service/{id}")
    public ResponseEntity<ServiceEntity> getDataById(@PathVariable Long id) {
        ServiceEntity serviceEntity = serviceEntityService.getDataById(id);
        return ResponseEntity.ok(serviceEntity);
    }

    @GetMapping("/v1/admin/configurations/service/all-list")
    public ResponseEntity<?> getAllList() {
        List<?> responseData = serviceEntityService.getAllList();

        return ResponseEntity.ok(ApiResponse.success("Fetched list", responseData));
    }

}
