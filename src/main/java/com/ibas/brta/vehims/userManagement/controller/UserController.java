package com.ibas.brta.vehims.userManagement.controller;

import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.userManagement.dao.UserDao;
import com.ibas.brta.vehims.userManagement.model.SUser;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.payload.response.*;
import com.ibas.brta.vehims.userManagement.payload.request.ChangePasswordRequest;
import com.ibas.brta.vehims.userManagement.payload.request.SUserRequest;
import com.ibas.brta.vehims.userManagement.payload.request.SUserUpdateRequest;
import com.ibas.brta.vehims.userManagement.payload.request.UserProfileRequest;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.userManagement.repository.SUserRepository;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import com.ibas.brta.vehims.security.CurrentUser;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.configurations.service.CommonService;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLServiceMediaRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceMediaResponse;
import com.ibas.brta.vehims.userManagement.service.PermissionService;
import com.ibas.brta.vehims.userManagement.service.RolePermissionService;
import com.ibas.brta.vehims.userManagement.service.RoleUService;
import com.ibas.brta.vehims.userManagement.service.UserService;
import com.ibas.brta.vehims.util.AppConstants;
import com.ibas.brta.vehims.util.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * To handle HTTP requests related to user operations and coordinating with the
 * application's business logic.
 * 
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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

    @Autowired
    private EntityManager entityManager;

    @Autowired
    SUserRepository sUserRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
                currentUser.getNameEn(), currentUser.getNameBn(), currentUser.getEmail());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getNameEn(),
                user.getCreatedAt());

        return userProfile;
    }

    // @author Ruhul Amin
    // @version 1.0

    @PostMapping("/v1/admin/user-management/user/create")
    public ResponseEntity<?> createData(@RequestBody @Valid SUserRequest request) {

        UserFullResponse saveData = userService.createData(request);

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

        UserFullResponse updatedData = userService.updateData(id, request);

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

        PagedResponse<UserFullResponse> responseData = userService.findAllBySearch(nameEn,
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
        UserFullResponse response = userService.getDataById(id);
        return ResponseEntity.ok(response);
    }

    // Get Auth User Profile
    @GetMapping("/v1/admin/user-management/user/get-auth-user-profile")
    public ResponseEntity<?> getAuthUserProfile() {
        UserFullResponse response = userService.getAuthUserProfile();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/auth/get-auth-user-by-id")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAuthUser(@CurrentUser UserPrincipal currentUser) {

        UserFullResponse userResponse = userService.getDataById(currentUser.getId());

        return ResponseEntity.ok(userResponse);
    }


    @PostMapping("/v1/auth/set-logged-in-user-office-role")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> setLoggedInUserOfficeRole(@CurrentUser UserPrincipal currentUser, @RequestBody UserOfficeRoleResponse request) throws InvocationTargetException, IllegalAccessException {


        Optional<SUser> user = sUserRepository.findById(currentUser.getId());

        UserResponse userResponse = new UserResponse();

        if (user.isPresent()) {
            SUser sUser = user.get();
            if (request.getOrgId() != null && request.getRoleId() != null) {

                sUser.setLoggedInOrgId(request.getOrgId());
                sUser.setLoggedInRoleId(request.getRoleId());

                SUser userSaved = sUserRepository.save(sUser);
                BeanUtils.copyProperties(userSaved, userResponse);
            } else {
                BeanUtils.copyProperties(sUser, userResponse);
            }
        }

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/v1/auth/unset-logged-in-user-office-role")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unsetLoggedInUserOfficeRole(@CurrentUser UserPrincipal currentUser) throws InvocationTargetException, IllegalAccessException {


        Optional<SUser> user = sUserRepository.findById(currentUser.getId());



        UserResponse userResponse = new UserResponse();

        if (user.isPresent()) {
            SUser sUser = user.get();
            sUser.setLastLoggedOutTime(Instant.now());
            sUserRepository.save(sUser);

            if (sUser.getLoggedInOrgId() != null && sUser.getLoggedInRoleId() != null) {

                sUser.setLoggedInOrgId(null);
                sUser.setLoggedInRoleId(null);
                SUser userSaved = sUserRepository.save(sUser);
                BeanUtils.copyProperties(userSaved, userResponse);
            } else {
                BeanUtils.copyProperties(user.get(), userResponse);
            }
        }

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/v1/auth/get-auth-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getSystemAdminRolePermission(@CurrentUser UserPrincipal currentUser) {

        Optional<SUser> user = sUserRepository.findById(currentUser.getId());
        UserFullResponse userResponse = userService.getDataById(currentUser.getId());

        List<Long> roleIds = commonService.getRoleIdsByUserId(currentUser.getId());

        if (roleIds != null) {
//            List<Long> permissionIds = commonService.getPermissionIdsByRoleIds(roleIds);
            if (user.get().getLoggedInRoleId() != null) {
                List<Long> permissionIds = commonService.getPermissionIdsByRoleId(user.get().getLoggedInRoleId());
                if (permissionIds != null) {
                    List<String> permissionCodes = commonService.getPermissionCodeByPermissionIds(permissionIds);
                    userResponse.setPermissionCodes(permissionCodes);
                }
            } else {
                List<Long> permissionIds = commonService.getPermissionIdsByRoleIds(roleIds);
                if (permissionIds!= null) {
                    List<String> permissionCodes = commonService.getPermissionCodeByPermissionIds(permissionIds);
                    userResponse.setPermissionCodes(permissionCodes);
                }
            }
        }

        // Build the native SQL query
        String sql = "SELECT suor.user_id, suor.org_id, suor.role_id, " +
                "org.name_en, org.name_bn, " +
                "role.name_en, role.name_bn " +
                "FROM s_user_organization_roles suor " +
                "JOIN c_organizations org ON suor.org_id = org.org_id " +
                "JOIN u_roles role ON suor.role_id = role.role_id " +
                "WHERE suor.user_id = :userId";

        // Execute the query
        Query query = entityManager.createNativeQuery(sql)
                .setParameter("userId", currentUser.getId());

        // Get the raw result list
        List<Object[]> resultList = query.getResultList();

        // Convert raw results into DTOs
        List<UserOfficeRoleResponse> userOfficeRoles = new ArrayList<>();
        for (Object[] row : resultList) {
            Long userIdValue = (row[0] instanceof BigInteger) ? ((BigInteger) row[0]).longValue() : (Long) row[0];
            Long orgIdValue = (row[1] instanceof BigInteger) ? ((BigInteger) row[1]).longValue() : (Long) row[1];
            Long roleIdValue = (row[2] instanceof BigInteger) ? ((BigInteger) row[2]).longValue() : (Long) row[2];

            UserOfficeRoleResponse response = new UserOfficeRoleResponse(
                    userIdValue, // userId
                    orgIdValue, // orgId
                    roleIdValue, // roleId
                    (String) row[3], // orgNameEn
                    (String) row[4], // orgNameBn
                    (String) row[5], // roleNameEn
                    (String) row[6] // roleNameBn
            );
            userOfficeRoles.add(response);
        }

        // List<UserOfficeRoleResponse> userOfficeRoles =
        // commonService.getUserOfficeRolesByUserId(currentUser.getId());
        // log.info("userOfficeRoles ====== {}" + userOfficeRoles);
        userResponse.setUserOfficeRoles(userOfficeRoles);

        return ResponseEntity.ok(userResponse);
    }

    // Update an existing item
    @PostMapping("/v1/admin/user-management/user/change-password")
    public ResponseEntity<?> changePassword(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changeUserPassword(currentUser.getId(), request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/updated")
                .buildAndExpand().toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success("Your password has been changed."));
    }

    @GetMapping("v1/admin/user-management/user/get-tin-info")
    public ResponseEntity<?> getUserTinInfo(@CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(userService.getUserTinInfo(currentUser.getId()));
    }

    @PostMapping("v1/admin/user-management/user/upload-profile-photo")
    public ResponseEntity<?> uploadUserPhotoFile(@CurrentUser UserPrincipal currentUser,
            @RequestParam("attachment") MultipartFile attachment) {

        if (attachment.isEmpty()) {
            throw new RuntimeException("File is empty.");
        }
        if (!Utils.isFileTypeImage(attachment)) {
            return ResponseEntity
                    .badRequest().body("Invalid file type. Only JPG, PNG are allowed.");
        }

        log.info("currentUser.getId() =============== {}", currentUser.getId());

//        String fileExtension = StringUtils.getFilenameExtension(attachment.getOriginalFilename());
//        if (!"jpg".equalsIgnoreCase(fileExtension) && !"png".equalsIgnoreCase(fileExtension)) {
//            return ResponseEntity
//                    .badRequest().body("Invalid file type. Only JPG, PNG, and PDF are allowed.");
//        }

        return userService.uploadUserPhoto(attachment, currentUser.getId());
    }

    @GetMapping("v1/admin/user-management/user/get-profile-photo")
    public ResponseEntity<?> getUserPhotoFile(@CurrentUser UserPrincipal currentUser) {

        return userService.getUserPhoto(currentUser.getId());
    }

    @GetMapping("v1/admin/user-management/user/get-nid-info")
    public ResponseEntity<?> getUserNidInfo(@CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(userService.getUserNidInfo(currentUser.getId()));
    }

    @GetMapping("v1/admin/user-management/user/get-profile-info")
    public ResponseEntity<?> getUserProfileInfo(@CurrentUser UserPrincipal currentUser) {
        UserProfileResponse userProfileResponse = userService.getUserProfileInfo(currentUser.getId());
        return ResponseEntity.ok(userProfileResponse);
    }

    @PostMapping("v1/admin/user-management/user/update-profile-info")
    public ResponseEntity<?> updateUserProfileInfo(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody UserProfileRequest request) {

        UserProfileResponse userProfileResponse = userService.updateUserProfileInfo(currentUser.getId(), request);

        return ResponseEntity.ok(userProfileResponse);
    }

}
