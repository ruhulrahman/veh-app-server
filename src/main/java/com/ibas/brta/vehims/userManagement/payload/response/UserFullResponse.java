package com.ibas.brta.vehims.userManagement.payload.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserFullResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private String username;
    private String mobile;
    private String email;
    private Instant createdAt;

    @JsonIgnore
    private String password;

    private Long userTypeId;
    private Long designationId;
    private Boolean isProfileCompleted;
    private Boolean isActive;
    private String userTypeNameEn;
    private String userTypeNameBn;
    private String userTypeCode;
    private String designationNameEn;
    private String designationNameBn;
    private List<UserOfficeRoleResponse> userOfficeRoles;
    private List<String> permissionCodes;
}