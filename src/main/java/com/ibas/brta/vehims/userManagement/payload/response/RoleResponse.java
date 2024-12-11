package com.ibas.brta.vehims.userManagement.payload.response;

import com.ibas.brta.vehims.common.model.rbac.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    Long roleId;
    RoleName rolename;
    private String recordStatus;
    private String buttonSet;
    private String privilegeNames;
}
