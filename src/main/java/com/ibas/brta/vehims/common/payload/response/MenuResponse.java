package com.ibas.brta.vehims.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private Long menuId;
    private String menuNameEn;
    private String menuNameBn;
    private String menuCode;
    private Long parentMenuId;
    private String parentMenuName;
    private String recordStatus;
    private String roleName;
    private Long roleId;
    private String menuUrl;
}