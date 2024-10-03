package com.ibas.brta.vehims.payload.request;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SUserRequest {
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 60)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 60)
    @Column(name = "name_bn", nullable = true)
    private String nameBn;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 15)
    @Column(nullable = false)
    private String username;

    @NaturalId
    @NotBlank(message = "Mobile cannot be blank")
    @Size(max = 13)
    @Column(nullable = false)
    private String mobile;

    @NaturalId
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 60)
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    @NotNull(message = "User Type cannot be null")
    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    @NotNull(message = "Designation cannot be null")
    @Column(name = "designation_id", nullable = false)
    private Long designationId;

    @Column(name = "profile_completed", nullable = false)
    private Boolean isProfileCompleted;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
