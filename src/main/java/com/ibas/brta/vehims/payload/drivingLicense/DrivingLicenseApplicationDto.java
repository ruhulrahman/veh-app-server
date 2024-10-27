package com.ibas.brta.vehims.payload.drivingLicense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrivingLicenseApplicationDto {
    private Integer sl;
    private Integer serviceRequestId;
    private String serviceRequestNo;
    private String applicantName;
    private String applicantType;
    private String licenseType;
    private String drivingIssueAuthority;
    private String nid;
    private Date applicationDate;
    private String applicationStatus;
}
