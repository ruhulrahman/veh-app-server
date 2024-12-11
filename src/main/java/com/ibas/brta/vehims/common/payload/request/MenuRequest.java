package com.ibas.brta.vehims.common.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class MenuRequest {

    private Long menuId;
    private String menuNameEn;
    private String menuNameBn;
    private String menuCode;
    private Long parentMenuId;
    private String parentMenueNameEn;
    private String rolename;
    private String menuUrl;
}
