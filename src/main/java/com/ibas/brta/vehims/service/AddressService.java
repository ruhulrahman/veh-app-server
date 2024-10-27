package com.ibas.brta.vehims.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.Address;
import com.ibas.brta.vehims.payload.request.AddressRequest;
import com.ibas.brta.vehims.payload.request.VehicleOwnerRequest;
import com.ibas.brta.vehims.payload.response.AddressResponse;
import com.ibas.brta.vehims.payload.response.CountryResponse;
import com.ibas.brta.vehims.payload.response.LocationResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.payload.response.VehicleOwnerResponse;
import com.ibas.brta.vehims.repository.AddressRepository;
import com.ibas.brta.vehims.repository.CommonRepository;
import com.ibas.brta.vehims.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.repository.VehicleInfoRepository;
import com.ibas.brta.vehims.repository.VehicleOwnerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressService {

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
    VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    LocationService locationService;

    @Autowired
    StatusService statusService;

    // Create or Insert operation
    public AddressResponse createData(AddressRequest request) {

        Address requestObject = new Address();
        BeanUtils.copyProperties(request, requestObject);
        Address savedData = addressRepository.save(requestObject);

        AddressResponse response = new AddressResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public AddressResponse updateData(Long id, AddressRequest request) {

        Optional<Address> existingData = addressRepository.findById(id);

        if (existingData.isPresent()) {
            Address requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            Address updatedData = addressRepository.save(requestObject);

            AddressResponse response = new AddressResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Find a single record by ID
    public AddressResponse getDataById(Long id) {

        Address existingData = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        AddressResponse response = new AddressResponse();

        CountryResponse countryResponse = countryService.getDataById(existingData.getCountryId());
        response.setCountry(countryResponse);

        LocationResponse locationResponse = locationService.getDataById(existingData.getLocationId());
        response.setLocation(locationResponse);

        StatusResponse statusResponse = statusService.findStatusById(existingData.getAddressTypeId());
        response.setAddressType(statusResponse);

        BeanUtils.copyProperties(existingData, response);

        return response;
    }

}
