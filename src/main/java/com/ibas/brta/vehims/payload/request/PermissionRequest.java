package com.ibas.brta.vehims.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PermissionRequest {
    private Long id;

    @NotNull(message = "Parent Permission cannot be null")
    @Column(name = "parent_permission_id", nullable = false)
    private Long parentId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    // @NotBlank(message = "Name in Bangla cannot be blank")
    // @Size(max = 100)
    // @Column(name = "name_bn", nullable = false)
    // private String nameBn;

    @NotBlank(message = "Address cannot be blank")
    @Column(name = "permission_code", nullable = false)
    private String permissionCode;

    @NotNull(message = "Type cannot be null")
    @Column(name = "type", nullable = false)
    private Integer type;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
