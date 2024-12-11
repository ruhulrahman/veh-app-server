package com.ibas.brta.vehims.userManagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    private String name;
    private String nameEn;
    private String nid;
    private String dob;
    private String gender;
    private String permanentAddress;
    private String presentAddress;
    private String photo;
    private String spouse;
    private String father;
    private String mother;
    private String religion;
    private String mobile;

    // User Request
    @Size(min = 4, max = 40)
    private String nameBn;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    private String otp;

}
