package com.ibas.brta.vehims.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.model.Status;
import com.ibas.brta.vehims.payload.request.StatusRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.service.StatusService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class StatusController {
        // Endpoint to get server status
        @Autowired
        private StatusService statusService;

        private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

        @PostMapping("/v1/admin/configurations/status/create")
        public ResponseEntity<?> createData(@Valid @RequestBody Status requestData) {

                Status saveData = statusService.createStatus(requestData);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/status/create/")
                                .buildAndExpand(saveData.getNameEn()).toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
        }

        @PostMapping("/v1/admin/configurations/status/update")
        public ResponseEntity<?> updateData(@Valid @RequestBody StatusRequest requestData) {
                try {

                        logger.info("requestData: " + requestData.toString());

                        Status updatedData = statusService.updateStatus(requestData);

                        URI location = ServletUriComponentsBuilder
                                        .fromCurrentContextPath().path("/status/create/")
                                        .buildAndExpand(updatedData.getNameEn()).toUri();

                        return ResponseEntity.created(location)
                                        .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
                } catch (Exception e) {
                        // TODO: handle exception
                        throw e;
                }

        }

        // @PreAuthorize("hasAnyAuthority('READ_OP')")
        @GetMapping("/v1/admin/configurations/status/list")
        public PagedResponse<StatusResponse> getListBySearch(
                        @RequestParam(required = false) String nameEn,
                        @RequestParam(required = false) Long statusGroupId,
                        @RequestParam(required = false) Boolean isActive,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

                PagedResponse<StatusResponse> responseData = statusService.findAllBySearch(nameEn,
                                statusGroupId,
                                isActive,
                                page,
                                size);

                return responseData;
        }

        @PostMapping("/v1/admin/configurations/status/delete/{id}")
        public ResponseEntity<?> deleteDataById(@PathVariable Long id) {

                statusService.deleteStatusById(id);

                return ResponseEntity.noContent().build();
        }

}
