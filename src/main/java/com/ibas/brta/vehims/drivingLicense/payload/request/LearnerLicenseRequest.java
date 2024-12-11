package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearnerLicenseRequest {
    private Long id;

    @NotNull
    private Long dlServiceRequestId;

    private Long applicantId;

    private Long photoMediaId;

    private String learnerNumber;

    private String learnerApplicantId;

    private Integer rollNo;

    private Long officeId;

    private Long examVenueId;

    private LocalDateTime issueDate;

    private LocalDateTime expireDate;

    private Long dlLanguageId;

    private Long applicationTypeId;

    private Long licenseTypeId;

    private LocalDateTime examDate;

    private Long examStatusId;

    private LocalDateTime examAttendedDate;

    private Boolean isPaid;

}