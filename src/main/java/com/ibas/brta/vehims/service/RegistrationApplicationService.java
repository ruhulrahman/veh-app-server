package com.ibas.brta.vehims.service;

import com.ibas.brta.vehims.exception.BadRequestException;
import com.ibas.brta.vehims.iservice.IRegistrationApplication;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.projection.RegistrationApplications;
import com.ibas.brta.vehims.repository.RegistrationApplicationRepository;
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
            String applicationDate) {

        validatePageNumberAndSize(page, size);

        // Retrieve
        Pageable pageable = PageRequest.of(page, size);

        Date appDate = null;
        if (applicationDate != null && !applicationDate.isBlank()) {
            appDate = Utils.getDateFromString(applicationDate, "dd/MM/yyyy");
        }

        Page<RegistrationApplications> records = applicationRepository.searchVehRegApplications(serviceRequestNo,
                chassisNumber, engineNumber, nid, mobile, appDate, pageable);

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

    public PagedResponse<RegistrationApplicationResponse> getAuthUserVehicleRegistrationApplicationsV1(int page,
            int size,
            String serviceRequestNo, String chassisNumber, String engineNumber, String nid, String mobile,
            String applicationDate, Long userId) {

        validatePageNumberAndSize(page, size);

        // Retrieve
        Pageable pageable = PageRequest.of(page, size);

        Date appDate = null;
        if (applicationDate != null && !applicationDate.isBlank()) {
            appDate = Utils.getDateFromString(applicationDate, "dd/MM/yyyy");
        }

        Page<RegistrationApplications> records = applicationRepository.getAuthUserVehicleRegistrationApplicationsV1(
                serviceRequestNo,
                chassisNumber, engineNumber, nid, mobile, appDate, userId, pageable);

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
