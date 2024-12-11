package com.ibas.brta.vehims.userManagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PermissionResponse {
    private Long id;
    private Long parentId;
    private String parentName;
    private String nameEn;
    private String nameBn;
    private String permissionCode;
    private Integer type;
    private String typeName;
    private String pageUrl;
    private Boolean isActive;
}
