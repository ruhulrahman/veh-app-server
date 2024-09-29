package com.ibas.brta.vehims.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PermissionResponse {
    private Long id;
    private Long parentId;
    private String nameEn;
    private String permissionCode;
    private Integer type;
    private String typeName;
    private Boolean isActive;
}
