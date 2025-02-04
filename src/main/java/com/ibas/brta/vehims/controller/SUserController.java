package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.UserSummary;
import com.ibas.brta.vehims.payload.request.SUserRequest;
import com.ibas.brta.vehims.payload.request.SUserUpdateRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.SUserResponse;
import com.ibas.brta.vehims.payload.response.UserResponse;
import com.ibas.brta.vehims.security.CurrentUser;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.CommonService;
import com.ibas.brta.vehims.service.PermissionService;
import com.ibas.brta.vehims.service.RolePermissionService;
import com.ibas.brta.vehims.service.RoleUService;
import com.ibas.brta.vehims.service.UserService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/")
public class SUserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleUService roleService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    CommonService commonService;

    @PostMapping("/v1/admin/user-management/user/create")
    public ResponseEntity<?> createData(@RequestBody @Valid SUserRequest request) {

        SUserResponse saveData = userService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/user-management/user/update/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id,
            @Valid @RequestBody SUserUpdateRequest request) {

        SUserResponse updatedData = userService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/user-management/user/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        userService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/user-management/user/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) Long userTypeId,
            @RequestParam(required = false) Long designationId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<SUserResponse> responseData = userService.findAllBySearch(nameEn,
                email.trim(),
                mobile.trim(),
                userTypeId,
                designationId,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/user-management/user/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        SUserResponse response = userService.getDataById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/auth/get-auth-user-by-id")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserPrincipal currentUser) {

        SUserResponse userResponse = userService.getDataById(currentUser.getId());

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/v1/auth/get-auth-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getSystemAdminRolePermission(@CurrentUser UserPrincipal currentUser) {

        SUserResponse userResponse = userService.getDataById(currentUser.getId());

        List<Integer> roleIds = commonService.getRoleIdsByUserId(currentUser.getId());
        log.info("roleIds ========= {}", roleIds);
        if (roleIds != null) {
            List<Integer> permissionIds = commonService.getPermissionIdsByRoleIds(roleIds);
            log.info("permissionIds ========= {}", permissionIds);
            if (permissionIds != null) {
                List<String> permissionCodes = commonService.getPermissionCodeByPermissionIds(permissionIds);
                userResponse.setPermissionCodes(permissionCodes);
            }
        }

        return ResponseEntity.ok(userResponse);
    }

}
