package com.ibas.brta.vehims.drivingLicense.payload.response;

import com.ibas.brta.vehims.configurations.payload.response.BloodGroupResponse;
import com.ibas.brta.vehims.configurations.payload.response.OrganizationResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearnerDetailsResponse {
    private Long id;

    private Long dlServiceRequestId;

    private Long applicantId;

    private Long photoMediaId;

    private String learnerNumber;

    private String learnerApplicantId;

    private Integer rollNo;

    private Long officeId;

    private Long examVenueId;
    private String examVenueNameEn;
    private String examVenueNameBn;

    private LocalDateTime issueDate;

    private LocalDateTime expireDate;

    private Long dlLanguageId;

    private Long applicationTypeId;
    private StatusResponse applicationType;

    private Long licenseTypeId;
    private StatusResponse licenseType;

    private LocalDateTime examDate;
    private LocalDateTime examAttendedDate;

    private Long examStatusId;

    private UserResponse applicant;

    private UserNidInfoResponse userNidInfo;

    private BloodGroupResponse bloodGroup;

    private DLInformationResponse dlInformation;

    private OrganizationResponse issuedOffice;
    private DLServiceRequestResponse dlServiceRequest;

}