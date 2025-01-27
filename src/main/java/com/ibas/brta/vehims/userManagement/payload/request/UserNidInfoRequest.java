package com.ibas.brta.vehims.userManagement.payload.request;

import lombok.Data;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

@Data
public class UserNidInfoRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "NID number is required")
    @Size(max = 100, message = "NID number cannot exceed 100 characters")
    private String nidNumber;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dob;

    @NotNull(message = "Name in Bengali is required")
    @Size(max = 100, message = "Name in Bengali cannot exceed 100 characters")
    private String nameBn;

    @NotNull(message = "Name in English is required")
    @Size(max = 100, message = "Name in English cannot exceed 100 characters")
    private String nameEn;

    @Size(max = 100, message = "Father or Husband's Name in English cannot exceed 100 characters")
    private String fatherOrHusbandNameEn;

    @NotNull(message = "Father or Husband's Name in Bengali is required")
    @Size(max = 100, message = "Father or Husband's Name in Bengali cannot exceed 100 characters")
    private String fatherOrHusbandNameBn;

    @Size(max = 100, message = "Mother's Name in English cannot exceed 100 characters")
    private String motherNameEn;

    @NotNull(message = "Mother's Name in Bengali is required")
    @Size(max = 100, message = "Mother's Name in Bengali cannot exceed 100 characters")
    private String motherNameBn;

    @Size(max = 100, message = "Address in English cannot exceed 100 characters")
    private String addressEn;

    @NotNull(message = "Address in Bengali is required")
    @Size(max = 100, message = "Address in Bengali cannot exceed 100 characters")
    private String addressBn;

    @NotNull(message = "Gender ID is required")
    private Long genderId;

    @NotNull(message = "Mobile number is required")
    @Size(max = 20, message = "Mobile number cannot exceed 20 characters")
    private String mobile;
}
