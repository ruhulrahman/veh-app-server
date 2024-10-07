package com.ibas.brta.vehims.payload.request;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SUserUpdateRequest {
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 60)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 60)
    private String nameBn;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 15)
    private String username;

    @NotBlank(message = "Mobile cannot be blank")
    @Size(min = 11, max = 11, message = "Mobile number must be exactly 11 digits")
    private String mobile;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 60)
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "User Type cannot be null")
    private Long userTypeId;

    @NotNull(message = "Designation cannot be null")
    private Long designationId;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
