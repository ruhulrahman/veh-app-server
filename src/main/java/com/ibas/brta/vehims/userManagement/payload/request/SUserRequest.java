package com.ibas.brta.vehims.userManagement.payload.request;

import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SUserRequest {
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 60)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 60)
    private String nameBn;

    @NaturalId
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 15)
    private String username;

    @NotBlank(message = "Mobile cannot be blank")
    @Size(max = 11)
    private String mobile;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 60)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100)
    private String password;

    @NotNull(message = "User Type cannot be null")
    private Long userTypeId;

    @NotNull(message = "Designation cannot be null")
    private Long designationId;

    private Boolean isProfileCompleted;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;

    private List<UserOfficeRoleRequest> userOfficeRoles;
}
