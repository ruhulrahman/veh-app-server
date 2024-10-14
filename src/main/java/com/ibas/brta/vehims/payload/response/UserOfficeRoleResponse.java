package com.ibas.brta.vehims.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserOfficeRoleResponse {
    private Long userId;
    private Long orgId;
    private Long roleId;
    private String orgNameEn;
    private String orgNameBn;
    private String roleNameEn;
    private String roleNameBn;
}