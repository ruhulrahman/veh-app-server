package com.ibas.brta.vehims.service;

import com.ibas.brta.vehims.iservice.IRegistrationDrivingLicense;
import com.ibas.brta.vehims.payload.drivingLicense.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.projection.RegistrationApplications;
import com.ibas.brta.vehims.repository.drivingLicense.DLInformationRepository;
import com.ibas.brta.vehims.util.FilterModelMapper;
import com.ibas.brta.vehims.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DrivingLicenseService implements IRegistrationDrivingLicense {

    @Autowired
    DLInformationRepository drivingLicenseRepository;

    @Override
    public PagedResponse<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(int page, int size, String serviceRequestNo, String nid, String learnerNo, String mobile, Date applicationDate) {
        Utils.validatePageNumberAndSize(page, size);

        // Retrieve
        Pageable pageable = PageRequest.of(page, size);

        Page<DrivingLicenseApplicationDto> records = drivingLicenseRepository.searchDrivingLicenseApplications(serviceRequestNo,
                nid, learnerNo, mobile, applicationDate, pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(),
                    records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        return new PagedResponse<>(records.stream().toList(), records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }
}
