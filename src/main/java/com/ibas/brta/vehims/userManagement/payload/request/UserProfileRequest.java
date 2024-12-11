package com.ibas.brta.vehims.userManagement.payload.request;

import java.time.LocalDate;

import org.hibernate.annotations.NaturalId;

import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 60)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 60)
    private String nameBn;

    @NotBlank(message = "Mobile cannot be blank")
    @Size(max = 11)
    private String mobile;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 60)
    private String email;

    private Boolean isProfileCompleted;

    private UserDetailRequest userDetails;
}
