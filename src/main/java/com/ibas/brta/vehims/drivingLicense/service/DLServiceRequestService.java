package com.ibas.brta.vehims.drivingLicense.service;

import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
import com.ibas.brta.vehims.drivingLicense.model.DLInformation;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.model.DrivingLicenseClass;
import com.ibas.brta.vehims.drivingLicense.model.LearnerLicense;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLServiceRequestCreateRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceRequestDetailsResponse;
import com.ibas.brta.vehims.drivingLicense.repository.DLInformationRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceRequestRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DrivingLicenseClassRepository;
import com.ibas.brta.vehims.drivingLicense.repository.LearnerLicenseRepository;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.util.Utility;
import com.ibas.brta.vehims.util.Utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DLServiceRequestService {
    @Autowired
    DLServiceRequestRepository dlServiceRequestRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private DLInformationRepository dlInformationRepository;

    @Autowired
    private LearnerLicenseRepository learnerLicenseRepository;

    @Autowired
    DrivingLicenseClassRepository drivingLicenseClassRepository;

    public DLServiceRequestDetailsResponse updateMVISubmission(DLServiceRequestCreateRequest request) {
        Status dlAppForwardedForJustification = statusRepository
                .findByStatusCode("dl_app_forwarded_for_justification");

        DLServiceRequest existingData = dlServiceRequestRepository
                .findByServiceRequestNo(request.getServiceRequestNo());

        if (existingData != null) {
            DLServiceRequest requestObject = existingData;

            Optional<Status> dlExamStatus = statusRepository.findById(request.getDlExamStatusId());

            LearnerLicense learnerLicense = learnerLicenseRepository
                    .findByDlServiceRequestId(requestObject.getId());

            if (learnerLicense != null) {
                learnerLicense.setExamStatusId(request.getDlExamStatusId());
                learnerLicense.setExamAttendedDate(request.getDlExamDate());
                learnerLicenseRepository.save(learnerLicense);
            }

            if (dlExamStatus.isPresent()) {
                requestObject.setDlExamStatusId(dlExamStatus.get().getId());

                if (dlExamStatus.get().getStatusCode().equals("dl_recommendation_pass")) {

                    Status dlAppPrimaryApproved = statusRepository.findByStatusCode("dl_app_primary_approved");

                    if (dlAppPrimaryApproved != null) {
                        requestObject.setApplicationStatusId(dlAppPrimaryApproved.getId());
                    }
                }

                if (dlExamStatus.get().getStatusCode().equals("dl_recommendation_fail")) {

                    Status dlAppPrimaryRejected = statusRepository.findByStatusCode("dl_app_primary_rejected");

                    if (dlAppPrimaryRejected != null) {
                        requestObject.setApplicationStatusId(dlAppPrimaryRejected.getId());
                    }
                }
            }

            requestObject.setDlExamRemarks(request.getDlExamRemarks());
            requestObject.setDlExamDate(request.getDlExamDate());
            requestObject.setInspectorId(Utils.getLoggedinUserId());

            DLServiceRequest updatedData = dlServiceRequestRepository.save(requestObject);

            DLServiceRequestDetailsResponse response = new DLServiceRequestDetailsResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceId());
        }
    }

    public DLServiceRequestDetailsResponse updateApprovalByAuthority(DLServiceRequestCreateRequest request) {
        DLServiceRequest existingData = dlServiceRequestRepository
                .findByServiceRequestNo(request.getServiceRequestNo());

        if (existingData != null) {
            DLServiceRequest requestObject = existingData;
            requestObject.setApplicationStatusId(request.getApplicationStatusId());
            requestObject.setApprovalRemarks(request.getApprovalRemarks());
            requestObject.setApprovalId(Utility.getLoggedinUserId());

            StatusProjection status = commonRepository
                    .getStatusByStatusCodeOrId(String.valueOf(request.getApplicationStatusId()));

            if (status.getStatusCode().equals("dl_app_primary_approved")) {
                requestObject.setApprovalDate(LocalDateTime.now());

                // LearnerLicense learnerLicense = learnerLicenseRepository
                // .findByDlServiceRequestId(requestObject.getId());

                // if (learnerLicense != null) {
                // learnerLicense.setExamStatusId(request.getApplicationStatusId());
                // learnerLicenseRepository.save(learnerLicense);
                // }
            }

            if (status.getStatusCode().equals("dl_app_primary_rejected")
                    || status.getStatusCode().equals("dl_app_fical_rejected")) {
                requestObject.setRejectionDate(LocalDateTime.now());

                // LearnerLicense learnerLicense = learnerLicenseRepository
                // .findByDlServiceRequestId(requestObject.getId());

                // if (learnerLicense != null) {
                // learnerLicense.setExamStatusId(request.getApplicationStatusId());
                // learnerLicenseRepository.save(learnerLicense);
                // }
            }

            if (status.getStatusCode().equals("dl_app_final_approved")) {
                requestObject.setApprovalDate(LocalDateTime.now());

                Optional<DLInformation> dlInformation = dlInformationRepository
                        .findById(requestObject.getDlInfoId());

                if (dlInformation.isPresent()) {
                    // Generate Driving License No and Reference No
                    dlInformation.get().setDlNumber("DL" + Utils.generateDLNumber());
                    dlInformation.get().setDlReferenceNumber("REF" + Utils.generateDLNumber());
                    dlInformation.get().setIssuedOfficeId(requestObject.getOrgId());
                    dlInformation.get().setIssueDate(LocalDateTime.now());

                    List<DrivingLicenseClass> dlInformationClasses = drivingLicenseClassRepository
                            .findByDlServiceRequestId(existingData.getId());

                    if (dlInformationClasses != null) {
                        for (DrivingLicenseClass dlInformationClass : dlInformationClasses) {
                            dlInformationClass.setDlInfoId(dlInformation.get().getId());
                            drivingLicenseClassRepository.save(dlInformationClass);
                        }
                    }

                    if (dlInformation.get().getLicenseTypeId() == 51) {
                        // Professional licenses
                        dlInformation.get().setExpireDate(LocalDateTime.now().plusYears(10));
                    } else {
                        // Non-Professional licenses
                        dlInformation.get().setExpireDate(LocalDateTime.now().plusYears(5));
                    }

                    dlInformationRepository.save(dlInformation.get());
                }
            }

            if (status.getStatusCode().equals("dl_app_final_rejected")) {
                requestObject.setRejectionDate(LocalDateTime.now());
            }

            DLServiceRequest updatedData = dlServiceRequestRepository.save(requestObject);

            DLServiceRequestDetailsResponse response = new DLServiceRequestDetailsResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceId());
        }
    }
}
