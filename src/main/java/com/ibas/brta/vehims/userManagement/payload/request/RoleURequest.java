package com.ibas.brta.vehims.userManagement.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleURequest {

    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;

    @NotBlank
    @Size(max = 100)
    @Column(name = "role_code", nullable = false, unique = true)
    private String roleCode;

    @NotNull(message = "permissionIds cannot be null")
    @NotEmpty(message = "permissionIds cannot be an empty array")
    private Long[] permissionIds;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
