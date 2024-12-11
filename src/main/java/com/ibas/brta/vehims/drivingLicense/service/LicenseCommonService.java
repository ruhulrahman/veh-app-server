package com.ibas.brta.vehims.drivingLicense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.CardDeliveryAddressResponse;
import com.ibas.brta.vehims.common.repository.AddressRepository;
import com.ibas.brta.vehims.common.repository.CardDeliveryAddressRepository;
import com.ibas.brta.vehims.common.service.AddressService;
import com.ibas.brta.vehims.common.service.CardDeliveryAddressService;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
import com.ibas.brta.vehims.drivingLicense.model.DLInformation;
import com.ibas.brta.vehims.drivingLicense.model.LearnerLicense;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLInformationResponse;
import com.ibas.brta.vehims.drivingLicense.payload.response.LearnerLicenseResponse;
import com.ibas.brta.vehims.drivingLicense.repository.DLInformationRepository;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceRequestRepository;
import com.ibas.brta.vehims.drivingLicense.repository.LearnerLicenseRepository;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LicenseCommonService {
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
    CardDeliveryAddressService cardDeliveryAddressService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CardDeliveryAddressRepository cardDeliveryAddressRepository;

    @Autowired
    LearnerLicenseRepository learnerLicenseRepository;

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

    public LearnerLicenseResponse getLearnerByServiceRequestId(Long serviceRequestId) {

        LearnerLicense existingData = learnerLicenseRepository.findByDlServiceRequestId(serviceRequestId);

        if (existingData == null) {
            return null;
        }

        LearnerLicenseResponse response = new LearnerLicenseResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }
}
