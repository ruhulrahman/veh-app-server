package com.ibas.brta.vehims.util;

import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.model.rbac.RoleName;
import com.ibas.brta.vehims.model.rbac.Privilege;
import com.ibas.brta.vehims.payload.request.RoleRequest;
import com.ibas.brta.vehims.payload.response.RoleResponse;

import java.util.Collection;

public class RoleMapper {

    public static RoleResponse EntityToResponse(Role role) {
        RoleResponse response = new RoleResponse();


        response.setRoleId(role.getId());
        response.setRolename(role.getName());
        Collection<Privilege> privileges2 = role.getPrivileges();
        StringBuilder names = new StringBuilder();
        for (Privilege privilege : privileges2) {
            names.append(privilege.getName() + ",");
        }
        response.setPrivilegeNames(names.toString());

        return response;
    }

    public static Role RequestToEntity(RoleRequest request) {
        Role role = new Role();
        role.setName(RoleName.valueOf(request.getRolename()));
        return role;
    }
}