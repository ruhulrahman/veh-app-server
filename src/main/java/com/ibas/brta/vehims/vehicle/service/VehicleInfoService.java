package com.ibas.brta.vehims.vehicle.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.configurations.service.CountryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.vehicle.model.VServiceRequest;
import com.ibas.brta.vehims.vehicle.model.VehicleInfo;
import com.ibas.brta.vehims.vehicle.model.VehicleOwner;
import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;
import com.ibas.brta.vehims.vehicle.payload.request.VServiceRequestCreateRequest;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleOwnerRequest;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegPage1Request;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegPage2Request;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegPage3Request;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VServiceRequestResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleInfoResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleOwnerResponse;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
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

            AddressRequest addressRequest = new AddressRequest();
            BeanUtils.copyProperties(request.getAddressInfo(), addressRequest);
            addressRequest.setUserId(serviceRequest.getApplicantId());
            addressRequest.setLocationId(7L); // FIXME
            AddressResponse address = addressService.createData(addressRequest);

            VehicleOwnerRequest vehicleOwnerRequest = new VehicleOwnerRequest();
            BeanUtils.copyProperties(request.getVehicleOwner(), vehicleOwnerRequest);
            vehicleOwnerRequest.setServiceRequestId(serviceRequest.getId());
            vehicleOwnerRequest.setVehicleInfoId(serviceRequest.getVehicleInfoId());
            vehicleOwnerRequest.setGenderId(1L);// FIXME
            vehicleOwnerRequest.setAddressId(address.getId());

            VehicleOwner vehicleOwnerExistingData = vehicleOwnerRepository
                    .findByVehicleInfoIdAndServiceRequestId(serviceRequest.getId(), serviceRequest.getVehicleInfoId());
            if (vehicleOwnerExistingData != null) {
                VehicleOwner vehicleOwner = new VehicleOwner();
                BeanUtils.copyProperties(vehicleOwnerExistingData, vehicleOwner); // Exclude ID

                VehicleOwner updatedData = vehicleOwnerRepository.save(vehicleOwner);

            } else {
                VehicleOwnerResponse vehicleOwnerResponse = vehicleOwnerService.createData(vehicleOwnerRequest);
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

}
