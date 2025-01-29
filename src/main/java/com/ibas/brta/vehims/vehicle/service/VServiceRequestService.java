package com.ibas.brta.vehims.vehicle.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ibas.brta.vehims.common.payload.response.MediaResult;
import com.ibas.brta.vehims.configurations.model.*;
import com.ibas.brta.vehims.configurations.payload.response.*;
import com.ibas.brta.vehims.configurations.repository.*;
import com.ibas.brta.vehims.configurations.service.CountryService;
import com.ibas.brta.vehims.configurations.service.DocumentTypeService;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.model.UserDetail;
import com.ibas.brta.vehims.userManagement.repository.UserDetailRepository;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import com.ibas.brta.vehims.vehicle.payload.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.common.model.Address;
import com.ibas.brta.vehims.common.model.Media;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.vehicle.model.VServiceMedia;
import com.ibas.brta.vehims.vehicle.model.VServiceRequest;
import com.ibas.brta.vehims.vehicle.model.VehicleFitness;
import com.ibas.brta.vehims.vehicle.model.VehicleInfo;
import com.ibas.brta.vehims.vehicle.model.VehicleOwner;
import com.ibas.brta.vehims.vehicle.model.VehicleRegistration;
import com.ibas.brta.vehims.vehicle.payload.request.VServiceRequestCreateRequest;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.MediaResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import com.ibas.brta.vehims.vehicle.repository.VServiceMediaRepository;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleFitnessRepository;
import com.ibas.brta.vehims.common.repository.AddressRepository;
import com.ibas.brta.vehims.common.repository.MediaRepository;
import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.common.service.MediaService;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleOwnerRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleRegistrationRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.ibas.brta.vehims.util.Utility;

@Slf4j
@Service
public class VServiceRequestService {
    @Autowired
    VServiceRequestRepository serviceRequestRepository;

    @Autowired
    CountryService countryService;

    // @Autowired
    // VehicleInfoService vehicleInfoService;

    @Autowired
    VehicleInfoRepository vehicleInfoRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    UserNidInfoRepository userNidInfoRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    ServiceDocumentMapRepository serviceDocumentMapRepository;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    VServiceMediaRepository serviceMediaRepository;

    @Autowired
    MediaService mediaService;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    VehicleFitnessRepository vehicleFitnessRepository;

    @Autowired
    VehicleRegistrationRepository vehicleRegistrationRepository;

    @Autowired
    VehicleClassRepository vehicleClassRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    VehicleRegistrationMarkOrganizationMapRepository vehicleRegistrationMarkOrganizationMapRepository;

    @Autowired
    VehicleJointOwnerService vehicleJointOwnerService;

    @Autowired
    FuelTypeRepository fuelTypeRepository;

    @Autowired
    VehicleColorRepository vehicleColorRepository;

    // Create or Insert operation
    public VServiceRequestResponse createData(VServiceRequestCreateRequest request) {

        VServiceRequest requestObject = new VServiceRequest();
        BeanUtils.copyProperties(request, requestObject);

        requestObject.setServiceRequestNo(generateServiceRequestNo());
        requestObject.setApplicantId(Utility.getLoggedinUserId());
        VServiceRequest savedData = serviceRequestRepository.save(requestObject);

        VServiceRequestResponse response = new VServiceRequestResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    public String generateServiceRequestNo() {
        // This ensures the generated service_request_no will look like 2024051427339123
        // (2024 year, 05 month, 14 day, 27 hour, 33 minute, 39 second, and 123
        // milliseconds).
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time as 'yyyyMMddHHmmssSSS'
        // yyyy for the year (e.g., 2024)
        // MM for the month (e.g., 05 for May)
        // dd for the day (e.g., 14 for the 14th day of the month)
        // HH for the hour (24-hour format)
        // mm for the minute
        // ss for the second
        // SSS for milliseconds (to ensure uniqueness)
        // (YearMonthDayHourMinuteSecondMilliseconds)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);

        // Return the formatted date-time as the service request number
        return formattedDateTime;
    }

    // Update operation
    public VServiceRequestResponse updateData(Long id, VServiceRequestCreateRequest request) {

        Optional<VServiceRequest> existingData = serviceRequestRepository.findById(id);

        if (existingData.isPresent()) {
            VServiceRequest requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VServiceRequest updatedData = serviceRequestRepository.save(requestObject);

            VServiceRequestResponse response = new VServiceRequestResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Find a single record by ID
    public VServiceRequestResponse getDataById(Long id) {

        VServiceRequest existingData = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VServiceRequestResponse response = new VServiceRequestResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    // Find a single record by ID
    public VServiceRequestResponse getServiceRequestWithDetailsById(Long id) {

        VServiceRequest existingData = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VServiceRequestResponse response = new VServiceRequestResponse();
        BeanUtils.copyProperties(existingData, response);

        // log.info("existingData ====================== {}", existingData);

        if (existingData.getVehicleInfoId() != null) {
            VehicleInfo vehicleInfo = vehicleInfoRepository.findById(existingData.getVehicleInfoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Data not found with vehicle info id: " + existingData.getVehicleInfoId()));

            VehicleInfoResponse vehicleInfoResponse = new VehicleInfoResponse();

            BeanUtils.copyProperties(vehicleInfo, vehicleInfoResponse, "id");

            response.setVehicleInfo(vehicleInfoResponse);
        }

        if (existingData.getApplicantId() != null) {
            UserNidInfo userNidInfo = userNidInfoRepository.findByUserId(existingData.getApplicantId());

            if (userNidInfo != null) {

                UserNidInfoResponse userNidInfoResponse = new UserNidInfoResponse();

                BeanUtils.copyProperties(userNidInfo, userNidInfoResponse, "id");

                response.setApplicantNidInfo(userNidInfoResponse);
            }
        }

        if (existingData.getApplicantId() != null) {
            VehicleOwner vehicleOwner = vehicleOwnerRepository
                    .findByVehicleInfoIdAndServiceRequestId(existingData.getVehicleInfoId(), existingData.getId());

            // log.info("Vehicle Owner================== {}" + vehicleOwner);

            if (vehicleOwner != null) {

                VehicleOwnerResponse vehicleOwnerResponse = new VehicleOwnerResponse();

                BeanUtils.copyProperties(vehicleOwner, vehicleOwnerResponse);

                response.setVehicleOwner(vehicleOwnerResponse);

                if (vehicleOwner.getAddressId() != null) {

                    AddressResponse addressResponse = addressService.getDataById(vehicleOwner.getAddressId());

                    if (addressResponse != null) {
                        response.setAddressInfo(addressResponse);
                    }
                }
            }
        }

        if (existingData.getVehicleInfoId() != null) {
            List<VehicleJointOwnerResponse> vehicleJointOwners = vehicleJointOwnerService
                    .getJointOwnersByVehicleInfoIdAndServiceRequestId(existingData.getVehicleInfoId(),
                            existingData.getId());

            response.setVehicleJointOwners(vehicleJointOwners);
        }

        return response;
    }

    public DigitalRegistrationCertficateResponse getDigitalRegistrationCertificateDetailsReqeustById(Long id) {

        VServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        DigitalRegistrationCertficateResponse response = new DigitalRegistrationCertficateResponse();
        BeanUtils.copyProperties(serviceRequest, response);

        if (serviceRequest.getApplicantId() != null) {
//            UserDetail userDetail = userDetailRepository.findByUserId(serviceRequest.getApplicantId());
            Optional<User> user = userRepository.findById(serviceRequest.getApplicantId());
            if (user.isPresent() && user.get().getPhotoMediaId() != null) {
                MediaResult mediaResult = mediaService.getMediaByIdWithType(user.get().getPhotoMediaId());

                if (mediaResult != null) {
                    response.setOwnerPhotoInfo(mediaResult);
                }
            }
        }

        if (serviceRequest.getOrgId() != null) {
            Optional<Organization> organization = organizationRepository.findById(serviceRequest.getOrgId());
            if (organization.isPresent()) {
                OrganizationResponse organizationResponse = new OrganizationResponse();
                BeanUtils.copyProperties(organization.get(), organizationResponse);
                response.setIssuingAuthority(organizationResponse);
            }
        }

        Optional<VehicleRegistration> vehicleRegistration = vehicleRegistrationRepository.findByServiceRequestId(serviceRequest.getId());

        if (vehicleRegistration.isPresent()) {
            VehicleRegistrationResponse vehicleRegistrationResponse = new VehicleRegistrationResponse();
            BeanUtils.copyProperties(vehicleRegistration.get(), vehicleRegistrationResponse);
            response.setVehicleRegistration(vehicleRegistrationResponse);

            if (vehicleRegistrationResponse.getVehicleOwnerId() != null) {
//            if (serviceRequest.getApplicantId() != null) {

                log.info("VvehicleRegistrationResponse.getVehicleOwnerId()================== {}", vehicleRegistrationResponse.getVehicleOwnerId());
                log.info("VvehicleRegistrationResponse.getVehicleOwnerId()================== {}", serviceRequest.getId());
                VehicleOwner vehicleOwner = vehicleOwnerRepository.findByVehicleInfoIdAndServiceRequestId(vehicleRegistrationResponse.getVehicleOwnerId(), serviceRequest.getId());
//                VehicleOwner vehicleOwner = vehicleOwnerRepository.findByVehicleInfoIdAndServiceRequestId(serviceRequest.getApplicantId(), serviceRequest.getId());
                log.info("vehicleOwner================== {}", vehicleOwner);

                if (vehicleOwner != null) {

                    VehicleOwnerResponse vehicleOwnerResponse = new VehicleOwnerResponse();

                    BeanUtils.copyProperties(vehicleOwner, vehicleOwnerResponse);


                    if (vehicleOwner.getOwnerTypeId() != null) {

                        Optional<Status> ownerType = statusRepository.findById(vehicleOwner.getOwnerTypeId());

                        if (ownerType.isPresent()) {
                            StatusResponse statusResponse = new StatusResponse();
                            BeanUtils.copyProperties(ownerType.get(), statusResponse);
                            vehicleOwnerResponse.setOwnerType(statusResponse);
                        }
                    }

                    response.setVehicleOwner(vehicleOwnerResponse);

                    if (vehicleOwner.getAddressId() != null) {

                        AddressResponse addressResponse = addressService.getDataById(vehicleOwner.getAddressId());

                        if (addressResponse != null) {
                            response.setAddressInfo(addressResponse);
                        }
                    }
                }
            }
        }

        if (serviceRequest.getVehicleInfoId() != null) {
            VehicleInfoFullResponse vehicleInfoResponse = getFullVehicleInfoDataByVehicleInfoId(serviceRequest.getVehicleInfoId());
            response.setVehicleInfo(vehicleInfoResponse);
        }

        return response;
    }
    public FitnessCertficateResponse getFitnessCertificateDetailsReqeustById(Long id) {

        VServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        FitnessCertficateResponse response = new FitnessCertficateResponse();
        BeanUtils.copyProperties(serviceRequest, response);

        if (serviceRequest.getApplicantId() != null) {
//            UserDetail userDetail = userDetailRepository.findByUserId(serviceRequest.getApplicantId());

            Optional<User> user = userRepository.findById(serviceRequest.getApplicantId());
            if (user.isPresent() && user.get().getPhotoMediaId() != null) {
                MediaResult mediaResult = mediaService.getMediaByIdWithType(user.get().getPhotoMediaId());

                if (mediaResult != null) {
                    response.setOwnerPhotoInfo(mediaResult);
                }
            }

//            if (userDetail != null) {
//                MediaResult mediaResult = mediaService.getMediaByIdWithType(userDetail.getPhotoMediaId());
//
//                if (mediaResult != null) {
//                    response.setOwnerPhotoInfo(mediaResult);
//                }
//            }
        }

        if (serviceRequest.getOrgId() != null) {
            Optional<Organization> organization = organizationRepository.findById(serviceRequest.getOrgId());
            if (organization.isPresent()) {
                OrganizationResponse organizationResponse = new OrganizationResponse();
                BeanUtils.copyProperties(organization.get(), organizationResponse);
                response.setIssuingAuthority(organizationResponse);
            }
        }

        Optional<VehicleRegistration> vehicleRegistration = vehicleRegistrationRepository.findByServiceRequestId(serviceRequest.getId());

        if (vehicleRegistration.isPresent()) {
            VehicleRegistrationResponse vehicleRegistrationResponse = new VehicleRegistrationResponse();
            BeanUtils.copyProperties(vehicleRegistration.get(), vehicleRegistrationResponse);
            response.setVehicleRegistration(vehicleRegistrationResponse);

            if (vehicleRegistrationResponse.getVehicleOwnerId() != null) {

                VehicleOwner vehicleOwner = vehicleOwnerRepository.findByVehicleInfoIdAndServiceRequestId(vehicleRegistrationResponse.getVehicleOwnerId(), serviceRequest.getId());

                if (vehicleOwner != null) {

                    VehicleOwnerResponse vehicleOwnerResponse = new VehicleOwnerResponse();

                    BeanUtils.copyProperties(vehicleOwner, vehicleOwnerResponse);


                    if (vehicleOwner.getOwnerTypeId() != null) {

                        Optional<Status> ownerType = statusRepository.findById(vehicleOwner.getOwnerTypeId());

                        if (ownerType.isPresent()) {
                            StatusResponse statusResponse = new StatusResponse();
                            BeanUtils.copyProperties(ownerType.get(), statusResponse);
                            vehicleOwnerResponse.setOwnerType(statusResponse);
                        }
                    }

                    response.setVehicleOwner(vehicleOwnerResponse);

                    if (vehicleOwner.getAddressId() != null) {

                        AddressResponse addressResponse = addressService.getDataById(vehicleOwner.getAddressId());

                        if (addressResponse != null) {
                            response.setAddressInfo(addressResponse);
                        }
                    }
                }
            }
        }

        if (serviceRequest.getVehicleInfoId() != null) {
            VehicleInfoFullResponse vehicleInfoResponse = getFullVehicleInfoDataByVehicleInfoId(serviceRequest.getVehicleInfoId());
            response.setVehicleInfo(vehicleInfoResponse);
        }

        if (serviceRequest.getVehicleInfoId() != null) {
            Optional<VehicleFitness> vehicleFitness = vehicleFitnessRepository.findByVehicleInfoId(serviceRequest.getVehicleInfoId());
            if (vehicleFitness.isPresent()) {
                response.setFitnessValidStartDate(vehicleFitness.get().getFitnessValidStartDate());
                response.setFitnessValidEndDate(vehicleFitness.get().getFitnessValidEndDate());
            }
        }

        return response;
    }

    public VehicleOwnerResponse getVehicleOwnerDetailsReqeustById(Long id) {

        Optional<VehicleOwner> response = vehicleOwnerRepository.findById(id);

        if (response.isEmpty()) {
            return null;
        }

        VehicleOwnerResponse vehicleOwnerResponse = new VehicleOwnerResponse();
        BeanUtils.copyProperties(response.get(), vehicleOwnerResponse);

        return vehicleOwnerResponse;
    }

    public VehicleInfoFullResponse getFullVehicleInfoDataByVehicleInfoId(Long vehicleInfoId) {

        Optional<VehicleInfo> vehicleInfo = vehicleInfoRepository.findById(vehicleInfoId);

        if (vehicleInfo.isEmpty()) {
            return null;
        }

        VehicleInfoFullResponse response = new VehicleInfoFullResponse();

        BeanUtils.copyProperties(vehicleInfo.get(), response);

        if (response.getVehicleClassId() != null) {
            Optional<VehicleClass> vehicleClass = vehicleClassRepository.findById(response.getVehicleClassId());
            if (vehicleClass.isPresent()) {
                VehicleClassResponse vehicleClassResponse = new VehicleClassResponse();
                BeanUtils.copyProperties(vehicleClass.get(), vehicleClassResponse);
                response.setVehicleClass(vehicleClassResponse);
            }
        }

        if (response.getVehicleTypeId() != null) {
            Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(response.getVehicleTypeId());
            if (vehicleType.isPresent()) {
                VehicleTypeResponse vehicleTypeResponse = new VehicleTypeResponse();
                BeanUtils.copyProperties(vehicleType.get(), vehicleTypeResponse);
                response.setVehicleType(vehicleTypeResponse);
            }
        }

        if (response.getFuelId() != null) {
            Optional<FuelType> vehicleType = fuelTypeRepository.findById(response.getFuelId());
            if (vehicleType.isPresent()) {
                FuelTypeResponse fuelTypeResponse = new FuelTypeResponse();
                BeanUtils.copyProperties(vehicleType.get(), fuelTypeResponse);
                response.setFuelType(fuelTypeResponse);
            }
        }

        if (response.getBodyColorId() != null) {
            Optional<VehicleColor> vehicleColor = vehicleColorRepository.findById(response.getBodyColorId());
            if (vehicleColor.isPresent()) {
                VehicleColorResponse vehicleColorResponse = new VehicleColorResponse();
                BeanUtils.copyProperties(vehicleColor.get(), vehicleColorResponse);
                response.setVehicleColor(vehicleColorResponse);
            }
        }

        return response;
    }

    public VServiceRequestResponse updateInpectionByAuthority(VServiceRequestCreateRequest request) {

        // Setting Application Status and Forward for Inspection Date
        Status vehicleAppForwardedForJustification = statusRepository
                .findByStatusCode("vehicle_app_forwarded_for_justification");

        Optional<VServiceRequest> existingData = serviceRequestRepository
                .findByServiceRequestNo(request.getServiceRequestNo());

        if (existingData.isPresent()) {
            VServiceRequest requestObject = existingData.get();
            requestObject.setInspectorId(request.getInspectorId());
            requestObject.setForwardDateForInspection(LocalDateTime.now());
            requestObject.setApplicationStatusId(vehicleAppForwardedForJustification.getId());

            VServiceRequest updatedData = serviceRequestRepository.save(requestObject);

            VServiceRequestResponse response = new VServiceRequestResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceId());
        }
    }

    public VServiceRequestResponse updateInpectionByInspector(VServiceRequestCreateRequest request) {

        Optional<VServiceRequest> existingData = serviceRequestRepository
                .findByServiceRequestNo(request.getServiceRequestNo());

        if (existingData.isPresent()) {
            VServiceRequest requestObject = existingData.get();
            requestObject.setInspectionStatusId(request.getInspectionStatusId());
            requestObject.setInspectionRemarks(request.getInspectionRemarks());
            requestObject.setInspectionDate(LocalDateTime.now());

            VServiceRequest updatedData = serviceRequestRepository.save(requestObject);

            VServiceRequestResponse response = new VServiceRequestResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceId());
        }
    }

    public VServiceRequestResponse updateRevenueByAuthority(VServiceRequestCreateRequest request) {

        // Setting Application Status and Forward for Revenue Check Date
        Status vehicleAppForwardedForJustification = statusRepository
                .findByStatusCode("vehicle_app_forwarded_for_justification");

        Optional<VServiceRequest> existingData = serviceRequestRepository
                .findByServiceRequestNo(request.getServiceRequestNo());

        if (existingData.isPresent()) {
            VServiceRequest requestObject = existingData.get();
            requestObject.setRevenueCheckerId(request.getRevenueCheckerId());
            requestObject.setForwardDateForRevenue(LocalDateTime.now());
            requestObject.setApplicationStatusId(vehicleAppForwardedForJustification.getId());

            VServiceRequest updatedData = serviceRequestRepository.save(requestObject);

            VServiceRequestResponse response = new VServiceRequestResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceId());
        }
    }

    public VServiceRequestResponse updateRevenueByRevenueChecker(VServiceRequestCreateRequest request) {

        Optional<VServiceRequest> existingData = serviceRequestRepository
                .findByServiceRequestNo(request.getServiceRequestNo());

        if (existingData.isPresent()) {
            VServiceRequest requestObject = existingData.get();
            requestObject.setRevenueStatusId(request.getRevenueStatusId());
            requestObject.setRevenueRemarks(request.getRevenueRemarks());
            requestObject.setRevenueCheckDate(LocalDateTime.now());

            VServiceRequest updatedData = serviceRequestRepository.save(requestObject);

            VServiceRequestResponse response = new VServiceRequestResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceId());
        }
    }

    @Transactional
    public VServiceRequestResponse updateApprovalByAuthority(VServiceRequestCreateRequest request) {

        Optional<VServiceRequest> existingData = serviceRequestRepository
                .findByServiceRequestNo(request.getServiceRequestNo());

        if (existingData.isPresent()) {
            VServiceRequest requestObject = existingData.get();
            requestObject.setApplicationStatusId(request.getApplicationStatusId());
            requestObject.setApprovalRemarks(request.getApprovalRemarks());
            requestObject.setApprovalId(Utility.getLoggedinUserId());

            StatusProjection status = commonRepository
                    .getStatusByStatusCodeOrId(String.valueOf(request.getApplicationStatusId()));
            if (status.getStatusCode().equals("vehicle_app_final_approved")
                    || status.getStatusCode().equals("vehicle_app_primary_approved")) {
                requestObject.setApprovalDate(LocalDateTime.now());

                generateNewVehicleRegistrationNumber(existingData.get());
                createNewVehicleFitness(existingData.get());

            }
            if (status.getStatusCode().equals("vehicle_app_final_rejected")
                    || status.getStatusCode().equals("vehicle_app_primary_rejected")) {
                requestObject.setRejectionDate(LocalDateTime.now());
            }

            VServiceRequest updatedData = serviceRequestRepository.save(requestObject);

            VServiceRequestResponse response = new VServiceRequestResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + request.getServiceId());
        }
    }

    @Transactional
    public void generateNewVehicleRegistrationNumber(VServiceRequest serviceRequest) {

        Optional<VehicleInfo> vehicleInfo = vehicleInfoRepository.findById(serviceRequest.getVehicleInfoId());

        if (vehicleInfo.isPresent()) {

            VehicleInfo vehicleInfoObject = vehicleInfo.get();

            Optional<VehicleClass> vehicleClassObect = vehicleClassRepository
                    .findById(vehicleInfoObject.getVehicleClassId());

            VehicleClass vehicleClass = vehicleClassObect.get();

            VehicleRegistration vehicleRegistrationRequest = new VehicleRegistration();

            vehicleRegistrationRequest.setServiceRequestId(serviceRequest.getServiceId());
            vehicleRegistrationRequest.setVehicleInfoId(serviceRequest.getVehicleInfoId());
            vehicleRegistrationRequest.setVehicleOwnerId(serviceRequest.getApplicantId());
            vehicleRegistrationRequest.setRegOfficeId(serviceRequest.getOrgId());
            vehicleRegistrationRequest.setVehicleTypeId(vehicleInfoObject.getVehicleTypeId());
            vehicleRegistrationRequest.setVehicleClassId(vehicleInfoObject.getVehicleClassId());
            Optional<VehicleRegistration> existVehicleReg = vehicleRegistrationRepository
                    .findByRegOfficeIdAndVehicleClassId(serviceRequest.getOrgId(), vehicleClass.getId());

            if (existVehicleReg.isPresent()) {
                vehicleRegistrationRequest
                        .setClassNumber(generateNewVehicleClassNumber(existVehicleReg.get(), vehicleClass));
                vehicleRegistrationRequest
                        .setVehicleNumber(generateNewVehicleNumber(existVehicleReg.get(), vehicleClass));

            } else {
                vehicleRegistrationRequest.setClassNumber(String.valueOf(vehicleClass.getStartNumber()));
                vehicleRegistrationRequest.setVehicleNumber(String.valueOf(0001));
            }

            String fullRegNumber = generateNewVehicleFullRegNumber(serviceRequest, vehicleRegistrationRequest,
                    vehicleClass);

            if (fullRegNumber != null) {
                vehicleRegistrationRequest.setFullRegNumber(fullRegNumber);
            } else {
                throw new EntityNotFoundException("Vehicle Registration Mark not found");
            }

            vehicleRegistrationRepository.save(vehicleRegistrationRequest);

            Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(vehicleInfo.get().getVehicleTypeId());
            if (vehicleType.isPresent()) {
                if (!vehicleType.get().getNameEn().equals("MOTOR CYCLE")) {

                    VehicleFitness vehicleFitness = new VehicleFitness();

                    vehicleFitness.setServiceRequestId(serviceRequest.getServiceId());
                    vehicleFitness.setVehicleInfoId(serviceRequest.getVehicleInfoId());
                    vehicleFitness.setFitnessValidStartDate(LocalDateTime.now());
                    vehicleFitness.setFitnessValidEndDate(LocalDateTime.now().plusYears(5));

                    vehicleFitnessRepository.save(vehicleFitness);
                }
            }
        }
    }

    public String generateNewVehicleClassNumber(VehicleRegistration vehicleRegistration, VehicleClass vehicleClass) {

        if (Integer.parseInt(vehicleRegistration.getVehicleNumber()) < 9999) {
            return String.valueOf(vehicleRegistration.getClassNumber());
        } else {
            String classNumber = vehicleRegistration.getClassNumber();
            int newClassNumber = Integer.parseInt(classNumber) + 1;

            if (newClassNumber > vehicleClass.getEndNumber()) {
                throw new EntityNotFoundException("Vehicle Class Number is full");
            }

            return String.valueOf(newClassNumber);
        }
    }

    public String generateNewVehicleNumber(VehicleRegistration vehicleRegistration, VehicleClass vehicleClass) {

        if (Integer.parseInt(vehicleRegistration.getVehicleNumber()) < 9999) {
            String vehicleNumber = vehicleRegistration.getVehicleNumber();
            int newVehicleNumber = Integer.parseInt(vehicleNumber) + 1;

            return String.valueOf(newVehicleNumber);
        } else {
            throw new EntityNotFoundException("Vehicle Number is full");
        }
    }

    public String generateNewVehicleFullRegNumber(VServiceRequest serviceRequest,
            VehicleRegistration vehicleRegistration, VehicleClass vehicleClass) {

        VehicleRegistrationMark vehicleRegistrationMark = vehicleRegistrationMarkOrganizationMapRepository
                .getVehicleRegistrationMarkByOrgId(serviceRequest.getOrgId());

        if (vehicleRegistrationMark != null) {

            String orgName = vehicleRegistrationMark.getNameEn();
            String classNumber = vehicleRegistration.getClassNumber();
            String vehicleNumber = vehicleRegistration.getVehicleNumber();

            return orgName + "-" + vehicleClass.getSymbolEn() + "-" + classNumber + "-" + vehicleNumber;

        } else {
            return "";
        }
    }

    @Transactional
    public void createNewVehicleFitness(VServiceRequest serviceRequest) {

        Optional<VehicleInfo> vehicleInfo = vehicleInfoRepository.findById(serviceRequest.getVehicleInfoId());

        if (vehicleInfo.isPresent()) {
            Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(vehicleInfo.get().getVehicleTypeId());
            if (vehicleType.isPresent()) {
                if (!vehicleType.get().getNameEn().equals("MOTOR CYCLE")) {

                    VehicleFitness vehicleFitness = new VehicleFitness();

                    vehicleFitness.setServiceRequestId(serviceRequest.getServiceId());
                    vehicleFitness.setVehicleInfoId(serviceRequest.getVehicleInfoId());
                    vehicleFitness.setFitnessValidStartDate(LocalDateTime.now());
                    vehicleFitness.setFitnessValidEndDate(LocalDateTime.now().plusYears(5));

                    vehicleFitnessRepository.save(vehicleFitness);
                }
            }
        }
    }

    public List<ServiceDocumentMapResponse> getServiceDocumentsByServiceRequestId(Long serviceRequestId) {

        Optional<VServiceRequest> serviceRequest = serviceRequestRepository
                .findById(serviceRequestId);

        if (!serviceRequest.isPresent()) {
            throw new EntityNotFoundException("Data not found with id: " + serviceRequestId);
        }

        Long serviceId = serviceRequest.get().getServiceId();

        List<ServiceDocumentMap> existingData = serviceDocumentMapRepository
                .findByServiceIdOrderByPriorityAsc(serviceId);

        List<ServiceDocumentMapResponse> responseData = new ArrayList<>();

        if (existingData.isEmpty()) {
            return responseData;
        }

        existingData.forEach(record -> {
            ServiceDocumentMapResponse response = new ServiceDocumentMapResponse();
            BeanUtils.copyProperties(record, response);

            DocumentTypeResponse documentTypeResponse = documentTypeService.getDataById(record.getDocumentTypeId());
            response.setDocumentType(documentTypeResponse);

            List<VServiceMedia> vServiceMedias = serviceMediaRepository.findByDocumentTypeIdAndServiceRequestId(
                    record.getDocumentTypeId(),
                    serviceRequestId);

            if (vServiceMedias != null && !vServiceMedias.isEmpty()) {
                response.setFileExist(true);

                List<FileResponse> fileResponses = new ArrayList<>();

                for (VServiceMedia vServiceMedia : vServiceMedias) {

                    Optional<Media> existingMedia = mediaRepository.findById(vServiceMedia.getMediaId());
                    if (existingMedia.isPresent()) {
                        Media media = existingMedia.get();
                        FileResponse fileResponse = new FileResponse();
                        fileResponse.setMediaId(media.getId());
                        fileResponse.setFileName(media.getFile());
                        fileResponses.add(fileResponse);
                    }
                }

                response.setFiles(fileResponses);

            } else {
                response.setFileExist(false);
            }

            // ServiceEntity serviceEntity =
            // serviceEntityService.getDataById(record.getServiceId());
            // ServiceEntityResponse serviceEntityResponse = new ServiceEntityResponse();
            // BeanUtils.copyProperties(serviceEntity, serviceEntityResponse);
            // response.setService(serviceEntityResponse);

            responseData.add(response);
        });

        return responseData;
    }
}
