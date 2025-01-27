package com.ibas.brta.vehims.vehicle.service;

import com.ibas.brta.vehims.exception.BadRequestException;
import com.ibas.brta.vehims.iservice.IRegistrationApplication;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.vehicle.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.projection.RegistrationApplications;
import com.ibas.brta.vehims.vehicle.repository.RegistrationApplicationRepository;
import com.ibas.brta.vehims.util.AppConstants;
import com.ibas.brta.vehims.util.FilterModelMapper;
import com.ibas.brta.vehims.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ashshakur.rahaman
 */

@Service
public class RegistrationApplicationService implements IRegistrationApplication {
    @Autowired
    RegistrationApplicationRepository applicationRepository;

    @Override
    public PagedResponse<RegistrationApplicationResponse> searchVehRegApplications(int page, int size,
            String serviceRequestNo, String chassisNumber, String engineNumber, String nid, String mobile,
            Date applicationDate, Long userId) {

        validatePageNumberAndSize(page, size);

        // Retrieve
        Pageable pageable = PageRequest.of(page, size);

        Long orgId = Utils.getLoggedInOrgId();

        Page<RegistrationApplications> records = applicationRepository.searchVehRegApplications(serviceRequestNo,
                chassisNumber, engineNumber, nid, mobile, applicationDate, orgId, userId, pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(),
                    records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<RegistrationApplicationResponse> responseData = records.map(record -> {
            return FilterModelMapper.ProjectionToResponse(record);
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
