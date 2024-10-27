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
public class GetDrivingLicenseApplicationRequest {
    String serviceRequestNo;
    String lernerNo;
    String nid;
    String mobile;
    Date applicationDate;
}
