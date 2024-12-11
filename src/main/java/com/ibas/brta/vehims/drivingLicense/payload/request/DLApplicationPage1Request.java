package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.payload.request.UserNidInfoRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DLApplicationPage1Request {

    // Service Request columns Start-------------------
    private String serviceRequestNo;

    private Long orgId;

    private Long dlInfoId;

    private Long serviceId;

    private Long applicantId;

    @Min(value = 1, message = "Volume number must be at least 1")
    private Integer volumeNo;

    @Min(value = 1, message = "Page number must be at least 1")
    private Integer pageNo;

    private Long applicationStatusId;

    @PastOrPresent(message = "Application date cannot be in the future")
    private LocalDateTime applicationDate;
    // Service Request columns End ----------------------

    private String dlNumber;

    private String dlReferenceNumber;

    private Long dlLanguageId;

    @NotNull(message = "Application type ID is required")
    private Long applicationTypeId;

    @NotNull(message = "Applicant type ID is required")
    private Long applicantTypeId;

    @NotNull(message = "License type ID is required")
    private Long licenseTypeId;

    @NotNull(message = "Blood group ID is required")
    private Long bloodGroupId;

    // @NotNull(message = "Vehicle class ID is required")
    private Long dlVehicleClassId;

    @NotNull(message = "Vehicle class ids is required")
    private List<Long> dlVehicleClassIds;

    private Long issuedOfficeId;

    @NotNull(message = "Educational qualification ID is required")
    private Long eduQualificationId;

    @NotNull(message = "Occupation ID is required")
    private Long occupationId;

    @NotNull(message = "Nationality ID is required")
    private Long nationalityId;

    @NotNull(message = "Marital status ID is required")
    private Long maritalStatusId;

    private String spouseName;

    private String spouseContactNo;

    private Boolean isOtherCitizenship;

    private UserNidInfoRequest applicantNidInfo;

    private String applicantImage;

    private Integer stageCompleted;

}