package com.ibas.brta.vehims.userManagement.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class RoleRequest {

    private int id;
    private String rolename;
    private String privileges;
    private String menus;
    private String remarks;
    private long status;
}
