package com.ibas.brta.vehims.configurations.controller;

import com.ibas.brta.vehims.configurations.model.Designation;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.DesignationResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.repository.DesignationRepository;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.configurations.service.DesignationService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class DesignationController {
        @Autowired
        DesignationService designationService;

        @Autowired
        DesignationRepository designationRepository;

        // private static final Logger logger =
        // LoggerFactory.getLogger(DesignationController.class);

        private Long getLoggedinUserId() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                        return userPrincipal.getId();
                }
                return null;
        }

        @PostMapping("/v1/admin/configurations/designation/create")
        public ResponseEntity<?> createDesignationV1(@Valid @RequestBody Designation designation) {

                // designation.setCreatedBy(2L);
                // designation.setCreatedBy(getLoggedinUserId());
                // logger.info("designation ==", designation);
                Designation _designation = designationService.saveDesignation(designation);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/designation/create/")
                                .buildAndExpand(_designation.getNameEn()).toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success(_designation.getNameEn() + " saved.", _designation));
        }

        @PostMapping("/v1/admin/configurations/designation/update")
        public ResponseEntity<?> updateDesignationV1(@Valid @RequestBody Designation designation) {

                Designation _designation = designationService.updateDesignation(designation);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/designation/create/")
                                .buildAndExpand(_designation.getNameEn()).toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success(_designation.getNameEn() + " updated.", _designation));
        }

        // @PreAuthorize("hasAnyAuthority('READ_OP')")
        @GetMapping("/v1/admin/configurations/designation/list")
        public PagedResponse<DesignationResponse> getDesignationListBySearch(
                        @RequestParam(required = false) String nameEn,
                        @RequestParam(required = false) Boolean isActive,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

                PagedResponse<DesignationResponse> responseData = designationService.getDesignationListBySearch(nameEn,
                                isActive,
                                page,
                                size);

                return responseData;
        }

        @PostMapping("/v1/admin/configurations/designation/delete/{id}")
        public ResponseEntity<?> deleteDesignationById(@PathVariable Long id) {

                designationService.deleteDesignationById(id);

                return ResponseEntity.noContent().build();
        }

        @GetMapping("/search-designation")
        public ResponseEntity<?> getDesignationBySearch(
                        @RequestParam(required = false) String nameEn,
                        @RequestParam(required = false) Boolean isActive,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

                PagedResponse<DesignationResponse> responseData = designationService.getDesignationBySearch(nameEn,
                                isActive,
                                page,
                                size);

                // return responseData;
                return ResponseEntity.ok(ApiResponse.success("Fetched list", responseData));
        }

        @GetMapping("/v1/designation/list")
        public ResponseEntity<?> designationList() {
                List<Designation> _designations = designationService.findAllDesignations();

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/designation/list/")
                                .buildAndExpand(_designations).toUri();

                // return ResponseEntity.created(location).body(ApiResponse.success("Fetched
                // list", _designations));
                return ResponseEntity.ok(ApiResponse.success("Fetched list", _designations));
        }

        @GetMapping("/v1/admin/configurations/designation/all-list")
        public ResponseEntity<?> findAllDesignationsOrderByLevelNumberAsc() {
                List<Designation> _designations = designationService.findAllDesignationsOrderByLevelNumberAsc();

                return ResponseEntity.ok(ApiResponse.success("Fetched list", _designations));
        }

        @GetMapping("/v1/admin/configurations/designation/parent-list")
        public ResponseEntity<?> getParentDesignationList() {
                List<Designation> _designations = designationService.getParentDesignationList();

                return ResponseEntity.ok(ApiResponse.success("Fetched list", _designations));
        }

}
