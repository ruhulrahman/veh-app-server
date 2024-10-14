package com.ibas.brta.vehims.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PermissionRequest {
    private Long id;

    @Column(name = "parent_permission_id")
    private Long parentId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    private String nameBn;

    @NotBlank(message = "Address cannot be blank")
    private String permissionCode;

    @NotNull(message = "Type cannot be null")
    private Integer type;

    private String pageUrl;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
