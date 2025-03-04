package com.ibas.brta.vehims.drivingLicense.payload.projections;

import java.time.LocalDateTime;

public interface DrivingLicenseApplicationProjection {
    Long getSl();
    Long getServiceRequestId();
    String getServiceRequestNo();
    String getApplicantName();
    String getApplicantType();
    String getLicenseType();
    String getDrivingIssueAuthority();
    String getNid();
    LocalDateTime getApplicationDate();
    Long getApplicationStatusId();
    String getApplicationStatus();
    String getApplicationStatusCode();
    String getApplicationStatusColor();
    Long getDlExamStatusId();
    String getDlExamRemarks();
    LocalDateTime getDlExamDate();
    String getApprovalRemarks();
    LocalDateTime getApprovalDate();
    LocalDateTime getRejectionDate();
    Boolean getIsLearnerFeePaid();
    Boolean getIsLicenseFeePaid();
}
