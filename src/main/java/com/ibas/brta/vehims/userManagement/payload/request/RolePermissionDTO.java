package com.ibas.brta.vehims.userManagement.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RolePermissionDTO {

    private Long id;

    @NotNull(message = "Role Id cannot be null")
    @Column(name = "role_id", nullable = false, unique = true)
    private Long roleId;

    @NotNull(message = "Permission Id cannot be null")
    @Column(name = "permission_id", nullable = false, unique = true)
    private Long permissionId;
}
