package com.ibas.brta.vehims.common.service;

import java.util.Optional;

import com.ibas.brta.vehims.configurations.service.CountryService;
import com.ibas.brta.vehims.configurations.service.LocationService;
import com.ibas.brta.vehims.configurations.service.StatusService;
import com.ibas.brta.vehims.vehicle.service.VServiceRequestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.common.model.Address;
import com.ibas.brta.vehims.common.model.CardDeliveryAddress;
import com.ibas.brta.vehims.common.payload.request.CardDeliveryAddressRequest;
import com.ibas.brta.vehims.common.payload.response.CardDeliveryAddressResponse;
import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import com.ibas.brta.vehims.common.repository.AddressRepository;
import com.ibas.brta.vehims.common.repository.CardDeliveryAddressRepository;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleOwnerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CardDeliveryAddressService {

    @Autowired
    CountryService countryService;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    UserNidInfoRepository userNidInfoRepository;

    @Autowired
    CardDeliveryAddressRepository cardDeliveryAddressRepository;

    @Autowired
    LocationService locationService;

    @Autowired
    StatusService statusService;

    // Create operation
    public CardDeliveryAddressResponse createData(CardDeliveryAddressRequest request) {

        CardDeliveryAddress requestObject = new CardDeliveryAddress();
        BeanUtils.copyProperties(request, requestObject);

        // Save and flush immediately
        CardDeliveryAddress savedData = cardDeliveryAddressRepository.saveAndFlush(requestObject);

        CardDeliveryAddressResponse response = new CardDeliveryAddressResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public CardDeliveryAddressResponse updateData(Long id, CardDeliveryAddressRequest request) {

        Optional<CardDeliveryAddress> existingData = cardDeliveryAddressRepository.findById(id);

        if (existingData.isPresent()) {
            CardDeliveryAddress requestObject = existingData.get();

            // Avoid overwriting ID or version fields if they exist
            Long originalId = requestObject.getId();
            BeanUtils.copyProperties(request, requestObject);
            requestObject.setId(originalId); // Ensure ID remains unchanged

            // Use saveAndFlush to persist immediately and avoid stale state issues
            CardDeliveryAddress updatedData = cardDeliveryAddressRepository.saveAndFlush(requestObject);

            CardDeliveryAddressResponse response = new CardDeliveryAddressResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Find a single record by ID
    public CardDeliveryAddressResponse getDataById(Long id) {

        CardDeliveryAddress existingData = cardDeliveryAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        CardDeliveryAddressResponse response = new CardDeliveryAddressResponse();
        BeanUtils.copyProperties(existingData, response);

        if (existingData.getLocationId() != null) {

            LocationResponse thanaResponse = locationService.getDataById(existingData.getLocationId());
            response.setLocation(thanaResponse);

            LocationResponse districtResponse = locationService.getDataById(thanaResponse.getParentId());
            response.setDistrictId(districtResponse.getId());

            LocationResponse divisionResponse = locationService.getDataById(districtResponse.getParentId());
            response.setDivisionId(divisionResponse.getId());
        }

        if (existingData.getAddressTypeId() != null) {
            StatusResponse statusResponse = statusService.findStatusById(existingData.getAddressTypeId());
            response.setAddressType(statusResponse);
        }

        return response;
    }

}
