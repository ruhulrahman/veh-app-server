package com.ibas.brta.vehims.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SDrivingLearnerLicensResponse {
    private Long id;

    private Long dlServiceRequestId;

    private Long applicantId;

    private Long photoMediaId;

    private Long learnerNumber;

    private Long learnerApplicantId;

    private Integer rollNo;

    private Long officeId;

    private Long examVenueId;

    private LocalDateTime issueDate;

    private LocalDateTime expireDate;

    private Long dlLanguageId;

    private Long applicationTypeId;

    private Long licenseTypeId;

    private LocalDateTime examDate;

    private Long examStatus;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

    private Integer versionNo;

}