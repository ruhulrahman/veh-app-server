package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.model.rbac.RoleName;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.RoleResponse;

import java.util.List;
import java.util.Optional;

public interface IRole {
    List<Role> getAllRecords();

    Optional<Role> findById(Long id);

    Optional<Role> findByName(RoleName rolename);

    Role save(Role role);

    public PagedResponse<RoleResponse> getAll(int page, int size);

    List<RoleResponse> getAllRoles();
}
