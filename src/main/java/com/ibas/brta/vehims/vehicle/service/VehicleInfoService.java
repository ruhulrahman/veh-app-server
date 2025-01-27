package com.ibas.brta.vehims.vehicle.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.common.service.MediaService;
import com.ibas.brta.vehims.configurations.service.CountryService;
import com.ibas.brta.vehims.configurations.service.DocumentTypeService;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceMedia;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLServiceMediaRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceMediaResponse;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceMediaRepository;

import com.ibas.brta.vehims.vehicle.payload.request.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.vehicle.model.VServiceMedia;
import com.ibas.brta.vehims.vehicle.model.VServiceRequest;
import com.ibas.brta.vehims.vehicle.model.VehicleInfo;
import com.ibas.brta.vehims.vehicle.model.VehicleOwner;
import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;
import com.ibas.brta.vehims.configurations.payload.response.DocumentTypeResponse;
import com.ibas.brta.vehims.common.model.Media;
import com.ibas.brta.vehims.common.payload.request.MediaRequest;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.MediaResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.common.repository.MediaRepository;
import com.ibas.brta.vehims.vehicle.payload.response.VServiceMediaResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VServiceRequestResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleInfoResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleOwnerResponse;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VServiceMediaRepository;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleOwnerRepository;
import com.ibas.brta.vehims.util.Utility;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleInfoService {

    @Autowired
    VehicleInfoRepository vehicleInfoRepository;

    @Autowired
    CountryService countryService;

    @Autowired
    VServiceRequestService serviceRequestService;

    @Autowired
    VServiceRequestRepository serviceRequestRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    UserNidInfoRepository userNidInfoRepository;

    @Autowired
    VehicleOwnerService vehicleOwnerService;

    @Autowired
    AddressService addressService;

    @Autowired
    VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    VServiceMediaRepository vServiceMediaRepository;

    @Autowired
    MediaService mediaService;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    VehicleJointOwnerService vehicleJointOwnerService;

    // Create or Insert operation
    @Transactional
    public VServiceRequestResponse storeVehicleRegPage1(VehicleRegPage1Request request) {

        if (request.getServiceRequestId() != null) {

            VServiceRequest serviceRequest = serviceRequestRepository.findById(request.getServiceRequestId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Data not found with id: " + request.getServiceRequestId()));

            Optional<VehicleInfo> existingData = vehicleInfoRepository.findById(serviceRequest.getVehicleInfoId());
            if (existingData.isPresent()) {
                VehicleInfo requestObject = existingData.get();

                BeanUtils.copyProperties(request, requestObject);
                requestObject.setId(request.getVehicleInfoId());

                VehicleInfo updatedData = vehicleInfoRepository.save(requestObject);

                VehicleInfoResponse response = new VehicleInfoResponse();
                BeanUtils.copyProperties(updatedData, response);

                VServiceRequestResponse vServiceRequestResponse = serviceRequestService
                        .getServiceRequestWithDetailsById(serviceRequest.getId());

                return vServiceRequestResponse;
            } else {
                VServiceRequestResponse response = serviceRequestService
                        .getServiceRequestWithDetailsById(serviceRequest.getId());

                return response;
            }
        } else {
            VehicleInfo requestObject = new VehicleInfo();
            BeanUtils.copyProperties(request, requestObject);
            VehicleInfo savedData = vehicleInfoRepository.save(requestObject);

            VServiceRequestCreateRequest serviceRequestCreateRequest = new VServiceRequestCreateRequest();

            serviceRequestCreateRequest.setApplicantId(Utility.getLoggedinUserId());

            StatusProjection statusDraft = commonRepository.getStatusByStatusCodeOrId("vehicle_app_draft");
            if (statusDraft != null) {
                serviceRequestCreateRequest.setApplicationStatusId(statusDraft.getId());
            }
            StatusProjection serviceData = commonRepository.getServiceByCodeOrId("motor_vehicle_registration");
            if (serviceData != null) {
                serviceRequestCreateRequest.setServiceId(serviceData.getId());
            }

            serviceRequestCreateRequest.setVehicleInfoId(savedData.getId());

            VServiceRequestResponse serviceRequest = serviceRequestService.createData(serviceRequestCreateRequest);

            VServiceRequestResponse response = serviceRequestService
                    .getServiceRequestWithDetailsById(serviceRequest.getId());

            return response;
        }
    }

    @Transactional
    public VehicleInfoResponse storeVehicleRegPage2(VehicleRegPage2Request request) {

        if (request.getServiceRequestId() != null) {

            VServiceRequest serviceRequest = serviceRequestRepository.findById(request.getServiceRequestId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Data not found with id: " + request.getServiceRequestId()));

            Optional<VehicleInfo> existingData = vehicleInfoRepository.findById(serviceRequest.getVehicleInfoId());

            if (existingData.isPresent()) {
                VehicleInfo requestObject = existingData.get();
                BeanUtils.copyProperties(request, requestObject);
                requestObject.setId(request.getVehicleInfoId());

                VehicleInfo updatedData = vehicleInfoRepository.save(requestObject);

                VehicleInfoResponse response = new VehicleInfoResponse();
                BeanUtils.copyProperties(updatedData, response);
                return response;
            } else {
                throw new EntityNotFoundException("Data not found with id: " + request.getId());
            }
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceRequestId());
        }

    }

    @Transactional
    public VehicleInfoResponse storeVehicleRegPage3(VehicleRegPage3Request request) {
        log.info("request==================== " + request);

        if (request.getServiceRequestId() != null) {

            VServiceRequest serviceRequest = serviceRequestRepository.findById(request.getServiceRequestId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Data not found with id: " + request.getServiceRequestId()));

            serviceRequest.setOrgId(request.getOrgId());
            serviceRequestRepository.save(serviceRequest);
            AddressRequest addressRequest = new AddressRequest();
            BeanUtils.copyProperties(request.getAddressInfo(), addressRequest);
            addressRequest.setUserId(serviceRequest.getApplicantId());
            // addressRequest.setLocationId(7L); // FIXME
            AddressResponse address;

            if (addressRequest.getId() != null) {
                address = addressService.updateData(addressRequest.getId(), addressRequest);
            } else {
                address = addressService.createData(addressRequest);
            }

            // AddressResponse addressExist =
            // addressService.getDataById(addressRequest.getId());
            // if (addressExist != null) {
            // address = addressService.updateData(addressExist.getId(), addressRequest);
            // } else {
            // address = addressService.createData(addressRequest);
            // }

            VehicleOwnerRequest vehicleOwnerRequest = new VehicleOwnerRequest();
            BeanUtils.copyProperties(request.getVehicleOwner(), vehicleOwnerRequest);
            vehicleOwnerRequest.setServiceRequestId(serviceRequest.getId());
            vehicleOwnerRequest.setVehicleInfoId(serviceRequest.getVehicleInfoId());
            vehicleOwnerRequest.setAddressId(address.getId());
            vehicleOwnerRequest.setIsPrimaryOwner(true);

            UserNidInfo userNidInfo = userNidInfoRepository.findByUserId(serviceRequest.getApplicantId());
            if (userNidInfo != null) {
                vehicleOwnerRequest.setGenderId(userNidInfo.getGenderId());
            }

            Long vehicleOwnerId;
            Boolean isJointOwner = vehicleOwnerRequest.getIsJointOwner();

            VehicleOwner vehicleOwnerExistingData = vehicleOwnerRepository
                    .findByVehicleInfoIdAndServiceRequestId(serviceRequest.getVehicleInfoId(), serviceRequest.getId());
            if (vehicleOwnerExistingData != null) {
                VehicleOwner vehicleOwner = new VehicleOwner();
                BeanUtils.copyProperties(vehicleOwnerExistingData, vehicleOwner); // Exclude ID
                BeanUtils.copyProperties(vehicleOwnerRequest, vehicleOwner); // Exclude ID

                VehicleOwner updatedData = vehicleOwnerRepository.save(vehicleOwner);
                vehicleOwnerId = updatedData.getId();

            } else {
                VehicleOwnerResponse vehicleOwnerResponse = vehicleOwnerService.createData(vehicleOwnerRequest);
                vehicleOwnerId = vehicleOwnerResponse.getId();
            }

            vehicleJointOwnerService.deleteByVehicleOwnerId(vehicleOwnerId);

            if (vehicleOwnerRequest.getIsJointOwner()) {
                for (VehicleJointOwnerRequest vehicleJointOwnerRequest : request.getVehicleJointOwners()) {
                    vehicleJointOwnerRequest.setServiceRequestId(serviceRequest.getId());
                    vehicleJointOwnerRequest.setVehicleInfoId(serviceRequest.getVehicleInfoId());
                    vehicleJointOwnerRequest.setVehicleOwnerId(vehicleOwnerId);
                    vehicleJointOwnerService.createData(vehicleJointOwnerRequest);
                }
            }

            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(serviceRequest, response);
            return response;

        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceRequestId());
        }

    }

    // Update operation
    public VehicleInfoResponse updateData(Long id, VehicleRegPage1Request request) {

        Optional<VehicleInfo> existingData = vehicleInfoRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleInfo requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleInfo updatedData = vehicleInfoRepository.save(requestObject);

            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (vehicleInfoRepository.existsById(id)) {
            vehicleInfoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleInfoResponse> findAllBySearch(
            String chassisNumber, String engineNumber,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<VehicleInfoResponse> records = vehicleInfoRepository.findListWithPaginationBySearchWithNativeQuery(
                chassisNumber,
                engineNumber,
                pageable);

        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleInfoResponse> responseData = records.map(record -> {
            VehicleInfoResponse response = new VehicleInfoResponse();
            BeanUtils.copyProperties(record, response);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public VehicleInfoResponse getDataById(Long id) {

        VehicleInfo existingData = vehicleInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleInfoResponse response = new VehicleInfoResponse();

        // userNidInfoRepository.findByUserId(id);
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    @Transactional
    public VServiceMediaResponse uploadDocument(VServiceMediaRequest request) {

        if (request.getServiceRequestId() != null) {

            Optional<VServiceRequest> existingServiceRequest = serviceRequestRepository
                    .findById(request.getServiceRequestId());

            if (existingServiceRequest.isPresent()) {

                MediaRequest mediaRequest = new MediaRequest();

                BeanUtils.copyProperties(request, mediaRequest);

                mediaRequest.setAttachmentFile(request.getAttachment());

                MediaResponse mediaResponse = mediaService.uploadMedia(mediaRequest);

                if (request.getMediaId() != null) {
                    mediaService.deleteDlServiceMediaId(request.getMediaId());
                }

                VServiceMedia dlServiceMedia = new VServiceMedia();

                BeanUtils.copyProperties(request, dlServiceMedia, "id");

                dlServiceMedia.setServiceRequestId(existingServiceRequest.get().getId());
                dlServiceMedia.setVehicleInfoId(existingServiceRequest.get().getVehicleInfoId());
                dlServiceMedia.setMediaId(mediaResponse.getId());

                VServiceMedia savedDlServiceMedia = vServiceMediaRepository.save(dlServiceMedia);

                VServiceMediaResponse response = new VServiceMediaResponse();

                BeanUtils.copyProperties(savedDlServiceMedia, response);

                return response;

            } else {
                throw new EntityNotFoundException("Service Request Not Found");
            }
        } else {
            throw new EntityNotFoundException("Service Request Not Found");
        }
    }

    public List<VServiceMediaResponse> getServiceMediasByServiceRequestId(Long serviceRequestId) {

        if (serviceRequestId != null) {

            Optional<VServiceRequest> existingServiceRequest = serviceRequestRepository
                    .findById(serviceRequestId);

            if (existingServiceRequest.isPresent()) {

                List<VServiceMedia> records = vServiceMediaRepository
                        .findByServiceRequestId(existingServiceRequest.get().getId());

                List<VServiceMediaResponse> responseData = records.stream()
                        .map(dlServiceMedia -> {
                            VServiceMediaResponse response = new VServiceMediaResponse();
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

}
