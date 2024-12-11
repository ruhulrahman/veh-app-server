package com.ibas.brta.vehims.vehicle.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
import com.ibas.brta.vehims.configurations.service.CountryService;
import com.ibas.brta.vehims.projection.StatusProjection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.common.model.Address;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.vehicle.model.VServiceRequest;
import com.ibas.brta.vehims.vehicle.model.VehicleInfo;
import com.ibas.brta.vehims.vehicle.model.VehicleOwner;
import com.ibas.brta.vehims.vehicle.payload.request.VServiceRequestCreateRequest;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VServiceRequestResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleInfoResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleOwnerResponse;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.common.repository.AddressRepository;
import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleOwnerRepository;

import jakarta.persistence.EntityNotFoundException;
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
    UserNidInfoRepository userNidInfoRepository;

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
                    .findByVehicleInfoIdAndServiceRequestId(existingData.getId(), existingData.getVehicleInfoId());

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
}
