package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.payload.drivingLicense.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Date;

public interface IRegistrationDrivingLicense {
    PagedResponse<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(int page, int size,
                                                                                 String serviceRequestNo, String nid,
                                                                                 String learnerNo, String mobile,
                                                                                 Date applicationDate);
}
