package com.ibas.brta.vehims.controller;

import java.util.List;
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

import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.StatusGroupResponse;
import com.ibas.brta.vehims.service.StatusGroupService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class StatusGroupController {
        // private static final Logger logger =
        // LoggerFactory.getLogger(StatusGroupController.class);

        @Autowired
        private StatusGroupService statusGroupService;

        @PostMapping("/v1/admin/configurations/status-group/create")
        public ResponseEntity<?> createDesignationV1(@Valid @RequestBody StatusGroup requestData) {

                StatusGroup saveData = statusGroupService.createStatusGroup(requestData);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/status-group/create/")
                                .buildAndExpand(saveData.getNameEn()).toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
        }

        @PostMapping("/v1/admin/configurations/status-group/update")
        public ResponseEntity<?> updateDesignationV1(@Valid @RequestBody StatusGroup requestData) {

                StatusGroup updatedData = statusGroupService.updateStatusGroup(requestData);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/designation/create/")
                                .buildAndExpand(updatedData.getNameEn()).toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
        }

        // @PreAuthorize("hasAnyAuthority('READ_OP')")
        @GetMapping("/v1/admin/configurations/status-group/list")
        public PagedResponse<StatusGroupResponse> getListBySearch(
                        @RequestParam(required = false) String nameEn,
                        @RequestParam(required = false) Boolean isActive,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

                PagedResponse<StatusGroupResponse> responseData = statusGroupService.findAllBySearch(nameEn,
                                isActive,
                                page,
                                size);

                return responseData;
        }

        @PostMapping("/v1/admin/configurations/status-group/delete/{id}")
        public ResponseEntity<?> deleteDataById(@PathVariable Long id) {

                statusGroupService.deleteStatusGroupById(id);

                return ResponseEntity.noContent().build();
        }

        @GetMapping("/v1/admin/configurations/status-group/active-list")
        public ResponseEntity<?> getActiveList() {
                List<StatusGroup> responseData = statusGroupService.getActiveStatusGroups();

                return ResponseEntity.ok(ApiResponse.success("Fetched list", responseData));
        }

}
