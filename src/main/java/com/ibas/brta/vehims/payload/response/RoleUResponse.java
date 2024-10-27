package com.ibas.brta.vehims.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleUResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private String roleCode;
    private Boolean isActive;
    private List<Long> permissionIds;
}
