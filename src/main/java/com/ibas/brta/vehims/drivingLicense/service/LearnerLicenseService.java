package com.ibas.brta.vehims.drivingLicense.service;

import com.ibas.brta.vehims.configurations.payload.response.OrganizationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserResponse;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.drivingLicense.model.DLInformation;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.model.LearnerLicense;
import com.ibas.brta.vehims.drivingLicense.payload.request.LearnerLicenseRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLInformationResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceRequestResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.LearnerDetailsResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.LearnerLicenseResponse;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.CardDeliveryAddressResponse;
import com.ibas.brta.vehims.common.repository.AddressRepository;
import com.ibas.brta.vehims.common.repository.CardDeliveryAddressRepository;
import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.configurations.model.ExamCenter;
import com.ibas.brta.vehims.configurations.model.Organization;
import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.ExamCenterRepository;
import com.ibas.brta.vehims.configurations.repository.OrganizationRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DLInformationRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceRequestRepository;
import com.ibas.brta.vehims.drivingLicense.repository.LearnerLicenseRepository;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import com.ibas.brta.vehims.util.Utils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LearnerLicenseService {

    @Autowired
    DLInformationRepository drivingLicenseRepository;

    @Autowired
    DLServiceRequestRepository serviceRequestRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    UserNidInfoRepository userNidInfoRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CardDeliveryAddressRepository cardDeliveryAddressRepository;

    @Autowired
    LearnerLicenseRepository learnerLicenseRepository;

    @Autowired
    LicenseCommonService licenseCommonService;

    @Autowired
    ExamCenterRepository examCenterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserNidInfoRepository getUserNidInfoRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    public LearnerLicenseResponse getLearnerLicenseByAuthUser() {

        LearnerLicense learnerLicense = learnerLicenseRepository.getItemByApplicantId(Utils.getLoggedinUserId());

        if (learnerLicense == null) {
            throw new EntityNotFoundException("Learner License Not Found");
        }

        LearnerLicenseResponse response = new LearnerLicenseResponse();
        BeanUtils.copyProperties(learnerLicense, response);

        return response;
    }

    public LearnerLicenseResponse createData(LearnerLicenseRequest request) {

        LearnerLicense requestObject = new LearnerLicense();
        BeanUtils.copyProperties(request, requestObject);

        // Save and flush immediately
        LearnerLicense savedData = learnerLicenseRepository.saveAndFlush(requestObject);

        LearnerLicenseResponse response = new LearnerLicenseResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public LearnerLicenseResponse updateData(Long id, LearnerLicenseRequest request) {

        Optional<LearnerLicense> existingData = learnerLicenseRepository.findById(id);

        if (existingData.isPresent()) {
            LearnerLicense requestObject = existingData.get();

            // Avoid overwriting ID or version fields if they exist
            Long originalId = requestObject.getId();
            BeanUtils.copyProperties(request, requestObject);
            requestObject.setId(originalId); // Ensure ID remains unchanged

            // Use saveAndFlush to persist immediately and avoid stale state issues
            LearnerLicense updatedData = learnerLicenseRepository.saveAndFlush(requestObject);

            LearnerLicenseResponse response = new LearnerLicenseResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Create or Insert operation
    @Transactional
    public LearnerLicenseResponse storeOrUpdateLearnerData(LearnerLicenseRequest request) {

        LearnerLicenseResponse existLearner = getDataByServiceRequestId(request.getDlServiceRequestId());

        LearnerLicenseRequest learnerLicenseRequest = new LearnerLicenseRequest();
        BeanUtils.copyProperties(request, learnerLicenseRequest);

        if (existLearner != null) {

            BeanUtils.copyProperties(existLearner, learnerLicenseRequest);

            return updateData(existLearner.getId(), learnerLicenseRequest);

        } else {

            // Create Learner License
            return createData(learnerLicenseRequest);
        }
    }

    // Find a single record by ID
    public LearnerLicenseResponse getDataById(Long id) {

        LearnerLicense existingData = learnerLicenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        LearnerLicenseResponse response = new LearnerLicenseResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public LearnerLicenseResponse getDataByExamDateAndVenue(LocalDateTime examDate, Long examVenueId) {

        LearnerLicense existingData = learnerLicenseRepository.findItemByExamDateAndExamVenueIdOrderByIdDesc(examDate,
                examVenueId);

        if (existingData == null) {
            // throw new EntityNotFoundException("No data found for the specified exam date
            // and venue ID");
            return null;
        }

        LearnerLicenseResponse response = new LearnerLicenseResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public LearnerLicenseResponse getDataByServiceRequestId(Long serviceRequestId) {

        LearnerLicense existingData = learnerLicenseRepository.findByDlServiceRequestId(serviceRequestId);

        if (existingData == null) {
            return null;
        }

        LearnerLicenseResponse response = new LearnerLicenseResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public LearnerDetailsResponse getLearnerDetailsByServiceRequestNo(String serviceRequestNo) {

        DLServiceRequest serviceRequest = serviceRequestRepository.findByServiceRequestNo(serviceRequestNo);

        if (serviceRequest == null) {
            return null;
        }

        DLServiceRequestResponse dlServiceRequestResponse = new DLServiceRequestResponse();
        BeanUtils.copyProperties(serviceRequest, dlServiceRequestResponse);

        Optional<Status> applicationStatus = statusRepository.findById(serviceRequest.getApplicationStatusId());

        if (applicationStatus.isPresent()) {
            StatusResponse applicationStatusResponse = new StatusResponse();
            BeanUtils.copyProperties(applicationStatus.get(), applicationStatusResponse);
            dlServiceRequestResponse.setApplicationStatus(applicationStatusResponse);
        }

        LearnerLicense learnerLicense = learnerLicenseRepository.findByDlServiceRequestId(serviceRequest.getId());

        if (learnerLicense == null) {
            return null;
        }

        LearnerDetailsResponse response = new LearnerDetailsResponse();
        BeanUtils.copyProperties(learnerLicense, response);

        response.setDlServiceRequest(dlServiceRequestResponse);

        if (learnerLicense.getOfficeId() != null) {

            Optional<Organization> organization = organizationRepository
                    .findById(learnerLicense.getOfficeId());

            if (organization.isPresent()) {
                OrganizationResponse organizationResponse = new OrganizationResponse();
                BeanUtils.copyProperties(organization.get(), organizationResponse);
                response.setIssuedOffice(organizationResponse);
            }
        }

        if (response.getLicenseTypeId() != null) {
            Optional<Status> licenseType = statusRepository.findById(response.getLicenseTypeId());

            if (licenseType.isPresent()) {
                StatusResponse licenseTypeResponse = new StatusResponse();
                BeanUtils.copyProperties(licenseType.get(), licenseTypeResponse);
                response.setLicenseType(licenseTypeResponse);
            }
        }

        if (response.getApplicationTypeId() != null) {
            Optional<Status> applicationType = statusRepository.findById(response.getApplicationTypeId());

            if (applicationType.isPresent()) {
                StatusResponse applicationTypeResponse = new StatusResponse();
                BeanUtils.copyProperties(applicationType.get(), applicationTypeResponse);
                response.setApplicationType(applicationTypeResponse);
            }
        }

        if (response.getApplicantId() != null) {
            UserNidInfo applicant = userNidInfoRepository.findByUserId(response.getApplicantId());

            if (applicant != null) {
                UserNidInfoResponse applicationResponse = new UserNidInfoResponse();
                BeanUtils.copyProperties(applicant, applicationResponse);
                response.setUserNidInfo(applicationResponse);
            }
        }

        Optional<ExamCenter> examCenter = examCenterRepository.findById(response.getExamVenueId());

        if (examCenter.isPresent()) {
            response.setExamVenueNameEn(examCenter.get().getAddressEn());
            response.setExamVenueNameBn(examCenter.get().getAddressBn());
        }

        Optional<DLInformation> dlInformation = drivingLicenseRepository.findById(serviceRequest.getDlInfoId());
        DLInformationResponse dlInformationResponse = new DLInformationResponse();

        if (dlInformation.isPresent()) {
            BeanUtils.copyProperties(dlInformation.get(), dlInformationResponse);

            List<Long> dlCLassIds = drivingLicenseRepository
                    .getApplicationDLClasseIdsByDlInfoId(serviceRequest.getDlInfoId());
            dlInformationResponse.setDlVehicleClassIds(dlCLassIds);

            if (dlInformationResponse.getPermanentAddressId() != null) {

                AddressResponse permaAddressResponse = addressService
                        .getDataById(dlInformationResponse.getPermanentAddressId());

                dlInformationResponse.setPermanentAddress(permaAddressResponse);

            }

            if (dlInformationResponse.getPresentAddressId() != null) {

                AddressResponse presentAddressResponse = addressService
                        .getDataById(dlInformationResponse.getPresentAddressId());
                dlInformationResponse.setPresentAddress(presentAddressResponse);
            }

            if (dlInformationResponse.getIssuedOfficeId() != null) {

                Optional<Organization> organization = organizationRepository
                        .findById(dlInformationResponse.getIssuedOfficeId());

                if (organization.isPresent()) {
                    OrganizationResponse organizationResponse = new OrganizationResponse();
                    BeanUtils.copyProperties(organization.get(), organizationResponse);
                    dlInformationResponse.setIssuedOffice(organizationResponse);
                }
            }

            response.setDlInformation(dlInformationResponse);
        }

        return response;
    }
}
