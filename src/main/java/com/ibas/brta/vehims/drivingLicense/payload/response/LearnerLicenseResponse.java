package com.ibas.brta.vehims.drivingLicense.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearnerLicenseResponse {
    private Long id;

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
    private LocalDateTime examAttendedDate;

    private Long examStatus;

    private List<Long> dlVehicleClassIds;

}