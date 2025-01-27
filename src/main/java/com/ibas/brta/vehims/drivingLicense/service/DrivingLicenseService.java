package com.ibas.brta.vehims.drivingLicense.service;

import com.ibas.brta.vehims.common.model.Media;
import com.ibas.brta.vehims.common.repository.MediaRepository;
import com.ibas.brta.vehims.iservice.IRegistrationDrivingLicense;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.drivingLicense.model.DLInformation;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceMedia;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.model.DrivingLicenseClass;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLApplicationPage1Request;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLApplicationPage2Request;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLServiceMediaRequest;
import com.ibas.brta.vehims.drivingLicense.payload.request.LearnerLicenseRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLApplicationResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLInformationResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceMediaResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceRequestDetailsResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.drivingLicense.payload.response.LearnerLicenseResponse;
import com.ibas.brta.vehims.common.model.Address;
import com.ibas.brta.vehims.common.model.CardDeliveryAddress;
import com.ibas.brta.vehims.common.payload.request.CardDeliveryAddressRequest;
import com.ibas.brta.vehims.common.payload.request.MediaRequest;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.CardDeliveryAddressResponse;
import com.ibas.brta.vehims.common.payload.response.MediaResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.common.repository.AddressRepository;
import com.ibas.brta.vehims.common.repository.CardDeliveryAddressRepository;
import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.common.service.CardDeliveryAddressService;
import com.ibas.brta.vehims.common.service.MediaService;
import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;
import com.ibas.brta.vehims.configurations.payload.response.DocumentTypeResponse;
import com.ibas.brta.vehims.configurations.payload.response.ExamCenterResponse;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
import com.ibas.brta.vehims.configurations.service.DocumentTypeService;
import com.ibas.brta.vehims.drivingLicense.repository.DLInformationRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceMediaRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceRequestRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DrivingLicenseClassRepository;
import com.ibas.brta.vehims.exception.FieldValidationException;
import com.ibas.brta.vehims.util.Utility;
import com.ibas.brta.vehims.util.Utils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DrivingLicenseService implements IRegistrationDrivingLicense {

    @Autowired
    DLInformationRepository drivingLicenseRepository;

    @Autowired
    DLServiceRequestRepository serviceRequestRepository;

    @Autowired
    CommonRepository commonRepository;

    // @Autowired
    // CommonService commonservice;

    @Autowired
    UserNidInfoRepository userNidInfoRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    CardDeliveryAddressService cardDeliveryAddressService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CardDeliveryAddressRepository cardDeliveryAddressRepository;

    @Autowired
    @Lazy
    LearnerLicenseService learnerLicenseService;

    @Autowired
    LicenseCommonService licenseCommonService;

    @Autowired
    DrivingLicenseClassRepository drivingLicenseClassRepository;

    @Autowired
    DLServiceMediaRepository dlServiceMediaRepository;

    @Autowired
    MediaService mediaService;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    DocumentTypeService documentTypeService;

    @Override
    public PagedResponse<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(int page, int size,
            String serviceRequestNo, String nid, String learnerNo, String mobile, Date applicationDate, Long userId) {
        Utils.validatePageNumberAndSize(page, size);

        // Retrieve
        Pageable pageable = PageRequest.of(page, size);

        Long orgId = Utils.getLoggedInOrgId();

        log.info("orgId================== {}", orgId);

        Page<DrivingLicenseApplicationDto> records = drivingLicenseRepository.searchDrivingLicenseApplications(
                serviceRequestNo,
                nid, learnerNo, mobile, applicationDate, orgId, userId, pageable);

        Long count = 1L;
        for (DrivingLicenseApplicationDto record : records.getContent()) {
            record.setSl(count);
            count++;
        }

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(),
                    records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        return new PagedResponse<>(records.stream().toList(), records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    public PagedResponse<DrivingLicenseApplicationDto> searchDrivingLicenseApplicationsForApplicant(int page, int size,
            String serviceRequestNo, String nid, String learnerNo, String mobile, Date applicationDate, Long userId) {
        Utils.validatePageNumberAndSize(page, size);

        // Retrieve
        Pageable pageable = PageRequest.of(page, size);

        Page<DrivingLicenseApplicationDto> records = drivingLicenseRepository.searchDrivingLicenseApplications(
                serviceRequestNo,
                nid, learnerNo, mobile, applicationDate, null, userId, pageable);

        Long count = 1L;
        for (DrivingLicenseApplicationDto record : records.getContent()) {
            record.setSl(count);
            count++;
        }

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(),
                    records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        return new PagedResponse<>(records.stream().toList(), records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Create or Insert operation
    @Transactional
    public DLApplicationResponse storeDLApplicationPage1(DLApplicationPage1Request request) {

        DLApplicationResponse response = new DLApplicationResponse();

        if (request.getServiceRequestNo() != null && !request.getServiceRequestNo().isEmpty()) {

            DLServiceRequest serviceRequest = serviceRequestRepository
                    .findByServiceRequestNo(request.getServiceRequestNo());

            if (serviceRequest != null) {

                Optional<DLInformation> existingData = drivingLicenseRepository.findById(serviceRequest.getDlInfoId());
                if (existingData.isPresent()) {
                    DLInformation dlInforRequestObject = existingData.get();

                    BeanUtils.copyProperties(request, dlInforRequestObject);

                    drivingLicenseRepository.save(dlInforRequestObject);

                    BeanUtils.copyProperties(getDLServiceRequestByServiceRequestNo(
                            serviceRequest.getServiceRequestNo()), response);
                } else {

                    BeanUtils.copyProperties(getDLServiceRequestByServiceRequestNo(
                            serviceRequest.getServiceRequestNo()), response);
                }
            } else {
                throw new EntityNotFoundException("Service Request Not Found");
            }
        } else {

            DLInformation dlInfoReqeustObject = new DLInformation();
            BeanUtils.copyProperties(request, dlInfoReqeustObject);
            dlInfoReqeustObject.setNidNumber(request.getApplicantNidInfo().getNidNumber());

            DLInformation savedData = drivingLicenseRepository.save(dlInfoReqeustObject);

            request.setApplicantId(Utility.getLoggedinUserId());

            StatusProjection statusDraft = commonRepository.getStatusByStatusCodeOrId("dl_app_draft");
            if (statusDraft != null) {
                request.setApplicationStatusId(statusDraft.getId());
            }

            StatusProjection serviceData = commonRepository.getServiceByCodeOrId("driving_license_issue");
            if (serviceData != null) {
                request.setServiceId(serviceData.getId());
            }

            request.setDlInfoId(savedData.getId());
            request.setServiceRequestNo(Utils.generateServiceRequestNo());

            DLServiceRequest newServiceRequest = new DLServiceRequest();
            BeanUtils.copyProperties(request, newServiceRequest);

            DLServiceRequest serviceRequest = serviceRequestRepository.save(newServiceRequest);

            BeanUtils.copyProperties(getDLServiceRequestByServiceRequestNo(
                    serviceRequest.getServiceRequestNo()), response);
        }

        LearnerLicenseRequest learnerLicenseRequest = new LearnerLicenseRequest();
        BeanUtils.copyProperties(request, learnerLicenseRequest);
        learnerLicenseRequest.setDlServiceRequestId(response.getServiceRequestId());
        learnerLicenseRequest.setApplicantId(Utils.getLoggedinUserId());
        // learnerLicenseRequest.setOfficeId(response.getOrgId());

        LearnerLicenseResponse learnerLicenseResponse = storeOrUpdateLearnerData(learnerLicenseRequest);

        drivingLicenseClassRepository.deleteByDlServiceRequestId(response.getServiceRequestId());

        for (Long dlCLassId : request.getDlVehicleClassIds()) {

            DrivingLicenseClass dlClassRequest = new DrivingLicenseClass();
            dlClassRequest.setDlServiceRequestId(response.getServiceRequestId());
            dlClassRequest.setDllId(learnerLicenseResponse.getId());
            dlClassRequest.setDlClassId(dlCLassId);

            drivingLicenseClassRepository.saveAndFlush(dlClassRequest);

        }

        return response;
    }

    // Create or Insert operation
    @Transactional
    public LearnerLicenseResponse storeOrUpdateLearnerData(LearnerLicenseRequest request) {

        LearnerLicenseResponse existLearner = learnerLicenseService
                .getDataByServiceRequestId(request.getDlServiceRequestId());

        LearnerLicenseRequest learnerLicenseRequest = new LearnerLicenseRequest();

        if (existLearner != null) {

            BeanUtils.copyProperties(existLearner, learnerLicenseRequest);
            BeanUtils.copyProperties(request, learnerLicenseRequest);

            return learnerLicenseService.updateData(existLearner.getId(), learnerLicenseRequest);

        } else {

            // Create Learner License
            BeanUtils.copyProperties(request, learnerLicenseRequest);
            return learnerLicenseService.createData(learnerLicenseRequest);
        }
    }

    // Create or Insert operation
    @Transactional
    public DLApplicationResponse storeDLApplicationPage2(DLApplicationPage2Request request) {

        if (request.getServiceRequestNo() != null && !request.getServiceRequestNo().isEmpty()) {

            DLServiceRequest existingServiceRequest = serviceRequestRepository
                    .findByServiceRequestNo(request.getServiceRequestNo());

            if (existingServiceRequest != null) {

                Optional<DLInformation> existingData = drivingLicenseRepository
                        .findById(existingServiceRequest.getDlInfoId());
                if (existingData.isPresent()) {
                    DLInformation dlInforRequestObject = existingData.get();

                    // Separate transaction for each address update
                    AddressResponse savedPresentAddress = updatePresentAddress(existingData.get(),
                            request.getPresentAddress());
                    AddressResponse savedPermanentAddress = updatePermanentAddress(existingData.get(),
                            request.getPermanentAddress());
                    CardDeliveryAddressResponse savedCardDeliveryAddress = updateCardDeliveryAddress(existingData.get(),
                            request.getCardDeliveryAddress());

                    dlInforRequestObject.setPresentAddressId(savedPresentAddress.getId());
                    dlInforRequestObject.setPermanentAddressId(savedPermanentAddress.getId());
                    dlInforRequestObject.setCardDeliveryAddressId(savedCardDeliveryAddress.getId());

                    // Save the updated DLInformation
                    DLInformation savedDLInformation = drivingLicenseRepository.saveAndFlush(dlInforRequestObject);

                    // Update and save the DLServiceRequest
                    DLApplicationResponse response = getDLServiceRequestByServiceRequestNo(
                            existingServiceRequest.getServiceRequestNo());

                    BeanUtils.copyProperties(request, existingServiceRequest);
                    existingServiceRequest.setId(existingServiceRequest.getId()); // Explicitly set ID for clarity

                    // StatusProjection statusPending =
                    // commonRepository.getStatusByStatusCodeOrId("dl_app_pending");
                    // if (statusPending != null) {
                    // // Application Status updated to pending
                    // existingServiceRequest.setApplicationStatusId(statusPending.getId());
                    // }
                    serviceRequestRepository.saveAndFlush(existingServiceRequest); // Ensure immediate DB

                    // storeOrUpdateLearnerData(learnerLicenseRequest);

                    LearnerLicenseResponse existLearner = learnerLicenseService
                            .getDataByServiceRequestId(existingServiceRequest.getId());

                    LearnerLicenseRequest learnerLicenseRequest = new LearnerLicenseRequest();

                    if (existLearner != null) {

                        BeanUtils.copyProperties(existLearner, learnerLicenseRequest);
                        // synchronization
                        // BeanUtils.copyProperties(request, learnerLicenseRequest);
                        learnerLicenseRequest.setDlServiceRequestId(response.getServiceRequestId());
                        learnerLicenseRequest.setOfficeId(response.getOrgId());
                        learnerLicenseRequest.setExamVenueId(request.getExamVenueId());

                        learnerLicenseService.updateData(existLearner.getId(), learnerLicenseRequest);

                    }

                    return response;
                } else {
                    return getDLServiceRequestByServiceRequestNo(existingServiceRequest.getServiceRequestNo());
                }
            } else {
                throw new EntityNotFoundException("Service Request Not Found");
            }
        } else {
            throw new EntityNotFoundException("Service Request Not Found");
        }
    }

    // Separate transactions for each address update to avoid lock conflicts
    @Transactional
    public AddressResponse updatePresentAddress(DLInformation existingData, AddressRequest request) {
        if (existingData.getPresentAddressId() != null) {
            return addressService.updateData(existingData.getPresentAddressId(), request);
        } else {
            return addressService.createData(request);
        }
    }

    @Transactional
    public AddressResponse updatePermanentAddress(DLInformation existingData, AddressRequest request) {
        if (existingData.getPermanentAddressId() != null) {
            return addressService.updateData(existingData.getPermanentAddressId(), request);
        } else {
            return addressService.createData(request);
        }
    }

    @Transactional
    public CardDeliveryAddressResponse updateCardDeliveryAddress(DLInformation existingData,
            CardDeliveryAddressRequest request) {
        if (existingData.getCardDeliveryAddressId() != null) {
            return cardDeliveryAddressService.updateData(existingData.getCardDeliveryAddressId(), request);
        } else {
            return cardDeliveryAddressService.createData(request);
        }
    }

    // Create or Insert operation
    @Transactional
    public DLServiceMediaResponse uploadLearnerDocument(DLServiceMediaRequest request) {

        if (request.getServiceRequestNo() != null && !request.getServiceRequestNo().isEmpty()) {

            DLServiceRequest existingServiceRequest = serviceRequestRepository
                    .findByServiceRequestNo(request.getServiceRequestNo());

            if (existingServiceRequest != null) {

                MediaRequest mediaRequest = new MediaRequest();

                BeanUtils.copyProperties(request, mediaRequest);

                mediaRequest.setAttachmentFile(request.getAttachment());

                MediaResponse mediaResponse = mediaService.uploadMedia(mediaRequest);

                if (request.getMediaId() != null) {
                    mediaService.deleteDlServiceMediaId(request.getMediaId());
                }

                DLServiceMedia dlServiceMedia = new DLServiceMedia();

                BeanUtils.copyProperties(request, dlServiceMedia, "id");

                dlServiceMedia.setServiceRequestId(existingServiceRequest.getId());
                dlServiceMedia.setDlInfoId(existingServiceRequest.getDlInfoId());
                dlServiceMedia.setMediaId(mediaResponse.getId());

                DLServiceMedia savedDlServiceMedia = dlServiceMediaRepository.save(dlServiceMedia);

                DLServiceMediaResponse response = new DLServiceMediaResponse();

                BeanUtils.copyProperties(savedDlServiceMedia, response);

                return response;

            } else {
                throw new EntityNotFoundException("Service Request Not Found");
            }
        } else {
            throw new EntityNotFoundException("Service Request Not Found");
        }
    }

    public List<DLServiceMediaResponse> getDlServiceMediasByServiceRequestNo(String serviceRequestNo) {

        if (serviceRequestNo != null && !serviceRequestNo.isEmpty()) {

            DLServiceRequest existingServiceRequest = serviceRequestRepository
                    .findByServiceRequestNo(serviceRequestNo);

            if (existingServiceRequest != null) {

                List<DLServiceMedia> records = dlServiceMediaRepository
                        .findByServiceRequestId(existingServiceRequest.getId());

                List<DLServiceMediaResponse> responseData = records.stream()
                        .map(dlServiceMedia -> {
                            DLServiceMediaResponse response = new DLServiceMediaResponse();
                            BeanUtils.copyProperties(dlServiceMedia, response);

                            Optional<Media> media = mediaRepository.findById(response.getMediaId());

                            if (media.isPresent()) {

                                MediaResponse mediaResponse = new MediaResponse();
                                BeanUtils.copyProperties(media.get(), mediaResponse);

                                response.setMedia(mediaResponse);
                                response.setFileName(mediaResponse.getFile());
                            }
                            // MediaResponse mediaResponse =
                            // mediaService.getDataById(response.getMediaId());
                            // response.setMedia(mediaResponse);
                            //
                            // if (mediaResponse != null) {
                            // response.setFileName(mediaResponse.getFile());
                            // }

                            DocumentTypeResponse documentType = documentTypeService
                                    .getDataById(response.getDocumentTypeId());
                            if (documentType != null) {
                                response.setDocumentTypeNameEn(documentType.getNameEn());
                                response.setDocumentTypeNameBn(documentType.getNameBn());
                            }
                            return response;
                        }).collect(Collectors.toList());

                return responseData;

            } else {
                throw new EntityNotFoundException("Service Request Not Found");
            }
        } else {
            throw new EntityNotFoundException("Service Request Not Found");
        }
    }

    @Transactional
    public DLApplicationResponse storeDLApplicationPage3(DLApplicationPage2Request request) {

        if (request.getServiceRequestNo() != null && !request.getServiceRequestNo().isEmpty()) {

            DLServiceRequest existingServiceRequest = serviceRequestRepository
                    .findByServiceRequestNo(request.getServiceRequestNo());

            if (existingServiceRequest != null) {

                Optional<DLInformation> existingData = drivingLicenseRepository
                        .findById(existingServiceRequest.getDlInfoId());
                if (existingData.isPresent()) {
                    DLInformation dlInforRequestObject = existingData.get();

                    BeanUtils.copyProperties(request, dlInforRequestObject);
                    dlInforRequestObject.setId(existingServiceRequest.getDlInfoId());
                    drivingLicenseRepository.save(dlInforRequestObject);

                    DLApplicationResponse response = getDLServiceRequestByServiceRequestNo(
                            existingServiceRequest.getServiceRequestNo());

                    request.setApplicationDate(LocalDateTime.now());
                    StatusProjection statusPending = commonRepository.getStatusByStatusCodeOrId("dl_app_pending");
                    if (statusPending != null) {
                        request.setApplicationStatusId(statusPending.getId());
                    }

                    // DLServiceRequest newServiceRequest = new DLServiceRequest();
                    // BeanUtils.copyProperties(request, newServiceRequest);

                    // log.info("newServiceRequest: {}", newServiceRequest);

                    BeanUtils.copyProperties(request, existingServiceRequest);
                    existingServiceRequest.setApplicantId(response.getApplicantId());

                    // Set the primary key value
                    existingServiceRequest.setId(existingServiceRequest.getId());

                    serviceRequestRepository.save(existingServiceRequest);

                    return response;
                } else {

                    return getDLServiceRequestByServiceRequestNo(
                            existingServiceRequest.getServiceRequestNo());
                }
            } else {
                throw new EntityNotFoundException("Service Request Not Found");
            }
        } else {
            throw new EntityNotFoundException("Service Request Not Found");
        }
    }

    public DLApplicationResponse getDLServiceRequestByServiceRequestNo(String ServiceRequestNo) {

        DLServiceRequest serviceRequest = serviceRequestRepository
                .findByServiceRequestNo(ServiceRequestNo);

        if (serviceRequest == null) {
            throw new EntityNotFoundException("Service Request Not Found");
        }

        DLApplicationResponse response = new DLApplicationResponse();
        BeanUtils.copyProperties(serviceRequest, response);
        response.setServiceRequestId(serviceRequest.getId());

        return response;
    }

    public DLServiceRequestDetailsResponse getDLServiceRequestDetailsByServiceRequestNo(String ServiceRequestNo) {

        DLServiceRequest serviceRequest = serviceRequestRepository
                .findByServiceRequestNo(ServiceRequestNo);

        if (serviceRequest == null) {
            throw new EntityNotFoundException("Service Request Not Found");
        }

        DLServiceRequestDetailsResponse response = new DLServiceRequestDetailsResponse();
        BeanUtils.copyProperties(serviceRequest, response);
        response.setServiceRequestId(serviceRequest.getId());

        List<Long> dlVehicleClassIds = drivingLicenseClassRepository
                .getDLClasseIdsByDlServiceRequestId(serviceRequest.getId());

        log.info(" serviceRequest.getId() =========== {}", response.getServiceRequestId());
        log.info(" dlVehicleClassIds =========== {}", dlVehicleClassIds);

        response.setDlVehicleClassIds(dlVehicleClassIds);

        UserNidInfo userNidInfo = userNidInfoRepository.findByUserId(response.getApplicantId());
        if (userNidInfo != null) {
            UserNidInfoResponse userNidInfoResponse = new UserNidInfoResponse();
            BeanUtils.copyProperties(userNidInfo, userNidInfoResponse);
            response.setApplicantNidInfo(userNidInfoResponse);
        }

        Optional<DLInformation> dlInformation = drivingLicenseRepository.findById(response.getDlInfoId());
        if (dlInformation.isPresent()) {
            DLInformationResponse dlInformationResponse = new DLInformationResponse();
            BeanUtils.copyProperties(dlInformation.get(), dlInformationResponse);

            List<Long> dlCLassIds = drivingLicenseRepository
                    .getApplicationDLClasseIdsByDlInfoId(response.getDlInfoId());
            dlInformationResponse.setDlVehicleClassIds(dlCLassIds);

            response.setDlInformation(dlInformationResponse);

            if (dlInformationResponse.getPermanentAddressId() != null) {

                AddressResponse permaAddressResponse = addressService
                        .getDataById(dlInformationResponse.getPermanentAddressId());

                response.setPermanentAddress(permaAddressResponse);

            }

            if (dlInformationResponse.getPresentAddressId() != null) {

                AddressResponse presentAddressResponse = addressService
                        .getDataById(dlInformationResponse.getPresentAddressId());
                response.setPresentAddress(presentAddressResponse);

            }

            if (dlInformationResponse.getCardDeliveryAddressId() != null) {

                CardDeliveryAddressResponse cardDeliveryAddressResponse = cardDeliveryAddressService
                        .getDataById(dlInformationResponse.getCardDeliveryAddressId());
                response.setCardDeliveryAddress(cardDeliveryAddressResponse);

            }
        }

        return response;
    }

    public DLInformationResponse getDLInformationDetailsById(Long dlInfoId) {

        Optional<DLInformation> dlInformation = drivingLicenseRepository.findById(dlInfoId);
        DLInformationResponse dlInformationResponse = new DLInformationResponse();

        if (dlInformation.isPresent()) {
            BeanUtils.copyProperties(dlInformation.get(), dlInformationResponse);

            List<Long> dlCLassIds = drivingLicenseRepository
                    .getApplicationDLClasseIdsByDlInfoId(dlInfoId);
            dlInformationResponse.setDlVehicleClassIds(dlCLassIds);

            if (dlInformationResponse.getPermanentAddressId() != null) {

                AddressResponse permaAddressResponse = addressService
                        .getDataById(dlInformationResponse.getPermanentAddressId());

                dlInformationResponse.setPermanentAddress(permaAddressResponse);

            }

            if (dlInformationResponse.getPresentAddressId() != null) {

                AddressResponse presentAddressResponse = addressService
                        .getDataById(dlInformationResponse.getPresentAddressId());
                log.info("presentAddressResponse: {}", presentAddressResponse);
                dlInformationResponse.setPresentAddress(presentAddressResponse);

            }

            if (dlInformationResponse.getCardDeliveryAddressId() != null) {

                CardDeliveryAddressResponse cardDeliveryAddressResponse = cardDeliveryAddressService
                        .getDataById(dlInformationResponse.getCardDeliveryAddressId());
                dlInformationResponse.setCardDeliveryAddress(cardDeliveryAddressResponse);

            }
        }

        return dlInformationResponse;
    }

    public DLApplicationResponse getDLServiceRequestByUser() {

        DLServiceRequest serviceRequest = serviceRequestRepository.getItemByApplicantId(Utils.getLoggedinUserId());

        if (serviceRequest == null) {
            throw new EntityNotFoundException("Service Request Not Found");
        }

        DLApplicationResponse response = new DLApplicationResponse();
        BeanUtils.copyProperties(serviceRequest, response);

        return response;
    }

    public Boolean checkDLServiceRequestAvailability() {

        List<Long> statusIds = commonRepository.getStatusIdsByStatusCodes(Arrays.asList(
                "dl_app_pending", "dl_app_primary_submitted", "dl_app_primary_approved", "dl_app_final_submitted",
                "dl_app_final_approved"));

        DLServiceRequest serviceRequest = serviceRequestRepository
                .getServiceRequestByApplicantIdAndApplicationStatusIds(Utils.getLoggedinUserId(), statusIds);

        return serviceRequest != null;
    }

    public DLServiceRequestDetailsResponse getServiceRequestByApplicantIdAndApplicationStatusIds() {

        List<Long> statusIds = commonRepository.getStatusIdsByStatusCodes(Arrays.asList(
                "dl_app_draft", "dl_app_primary_rejected", "dl_app_final_rejected"));

        DLServiceRequest serviceRequest = serviceRequestRepository
                .getServiceRequestByApplicantIdAndApplicationStatusIds(Utils.getLoggedinUserId(), statusIds);

        if (serviceRequest != null) {
            return getDLServiceRequestDetailsByServiceRequestNo(serviceRequest.getServiceRequestNo());
        } else {
            return null;
        }
    }
}
