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

import com.ibas.brta.vehims.configurations.payload.request.ExamCenterRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.ExamCenterResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.service.ExamCenterService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class ExamCenterController {

    @Autowired
    ExamCenterService examCenterService;

    @PostMapping("/v1/admin/configurations/office-exam-center/create")
    public ResponseEntity<?> createData(@Valid @RequestBody ExamCenterRequest request) {
        ExamCenterResponse saveData = examCenterService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/office-exam-center/")
                .buildAndExpand(saveData.getOrgNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getOrgNameEn() + " Exam center saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/office-exam-center/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody ExamCenterRequest request) {

        ExamCenterResponse updatedData = examCenterService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/office-exam-center/updated")
                .buildAndExpand(updatedData.getOrgNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getOrgNameEn() + " Exam center updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/office-exam-center/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        examCenterService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/office-exam-center/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) Long orgId,
            @RequestParam(required = false) Long thanaId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<ExamCenterResponse> responseData = examCenterService.findAllBySearch(
                orgId,
                thanaId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/office-exam-center/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        ExamCenterResponse response = examCenterService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
