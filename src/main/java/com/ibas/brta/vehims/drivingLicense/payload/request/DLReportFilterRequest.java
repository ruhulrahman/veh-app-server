package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.Data;

@Data
public class DLReportFilterRequest {
    Long orgId;
    Long licenseTypeId;
    String applicationDate;
}
