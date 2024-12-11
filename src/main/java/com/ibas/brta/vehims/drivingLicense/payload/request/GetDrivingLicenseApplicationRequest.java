package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDrivingLicenseApplicationRequest {
    String serviceRequestNo;
    String learnerNo;
    String nid;
    String mobile;
    Date applicationDate;
}
