package com.ibas.brta.vehims.drivingLicense.payload.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DrivingLicenseApplicationDto {
    private Long sl;
    private Long serviceRequestId;
    private String serviceRequestNo;
    private String applicantName;
    private String applicantType;
    private String licenseType;
    private String drivingIssueAuthority;
    private String nid;
    private LocalDateTime applicationDate;
    private Long applicationStatusId;
    private String applicationStatus;
    private String applicationStatusCode;
    private String applicationStatusColor;
    private Long dlExamStatusId;
    private String dlExamRemarks;
    private LocalDateTime dlExamDate;
    private String approvalRemarks;
    private LocalDateTime approvalDate;
    private LocalDateTime rejectionDate;
    private Boolean isLearnerFeePaid;
    private Boolean isLicenseFeePaid;

    public DrivingLicenseApplicationDto(Long serviceRequestId, String serviceRequestNo, String applicantName,
            String applicantType, String licenseType, String drivingIssueAuthority,
            String nid, LocalDateTime applicationDate, Long applicationStatusId,
            String applicationStatus, String applicationStatusCode, String applicationStatusColor) {

        this.serviceRequestId = serviceRequestId;
        this.serviceRequestNo = serviceRequestNo;
        this.applicantName = applicantName;
        this.applicantType = applicantType;
        this.licenseType = licenseType;
        this.drivingIssueAuthority = drivingIssueAuthority;
        this.nid = nid;
        this.applicationDate = applicationDate;
        this.applicationStatusId = applicationStatusId;
        this.applicationStatus = applicationStatus;
        this.applicationStatusCode = applicationStatusCode;
        this.applicationStatusColor = applicationStatusColor;
    }

    public DrivingLicenseApplicationDto(Long serviceRequestId, String serviceRequestNo, String applicantName,
            String applicantType, String licenseType, String drivingIssueAuthority,
            String nid, LocalDateTime applicationDate, Long applicationStatusId,
            String applicationStatus, String applicationStatusCode, String applicationStatusColor,
            Long dlExamStatusId, String dlExamRemarks, LocalDateTime dlExamDate, String approvalRemarks,
            LocalDateTime approvalDate, LocalDateTime rejectionDate, Boolean isLearnerFeePaid,
            Boolean isLicenseFeePaid) {

        this.serviceRequestId = serviceRequestId;
        this.serviceRequestNo = serviceRequestNo;
        this.applicantName = applicantName;
        this.applicantType = applicantType;
        this.licenseType = licenseType;
        this.drivingIssueAuthority = drivingIssueAuthority;
        this.nid = nid;
        this.applicationDate = applicationDate;
        this.applicationStatusId = applicationStatusId;
        this.applicationStatus = applicationStatus;
        this.applicationStatusCode = applicationStatusCode;
        this.applicationStatusColor = applicationStatusColor;
        this.dlExamStatusId = dlExamStatusId;
        this.dlExamRemarks = dlExamRemarks;
        this.dlExamDate = dlExamDate;
        this.approvalRemarks = approvalRemarks;
        this.approvalDate = approvalDate;
        this.rejectionDate = rejectionDate;
        this.isLearnerFeePaid = isLearnerFeePaid;
        this.isLicenseFeePaid = isLicenseFeePaid;
    }
}
