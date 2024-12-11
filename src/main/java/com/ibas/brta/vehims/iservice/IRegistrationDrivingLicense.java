package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;

import java.util.Date;

public interface IRegistrationDrivingLicense {
    PagedResponse<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(int page, int size,
                                                                                 String serviceRequestNo, String nid,
                                                                                 String learnerNo, String mobile,
                                                                                 Date applicationDate, Long userId);
}
