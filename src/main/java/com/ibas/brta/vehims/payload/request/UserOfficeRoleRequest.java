package com.ibas.brta.vehims.payload.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserOfficeRoleRequest {
    @NotNull(message = "Organization Id cannot be null")
    private Long orgId;

    @NotNull(message = "Role Id cannot be null")
    private Long roleId;
}