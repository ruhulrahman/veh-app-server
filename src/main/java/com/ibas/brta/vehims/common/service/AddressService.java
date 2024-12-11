package com.ibas.brta.vehims.common.service;

import java.util.Optional;

import com.ibas.brta.vehims.configurations.service.CountryService;
import com.ibas.brta.vehims.configurations.service.LocationService;
import com.ibas.brta.vehims.configurations.service.StatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.common.model.Address;
import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;
import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import com.ibas.brta.vehims.common.repository.AddressRepository;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.util.Utils;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleOwnerRepository;

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

        if (request.getUserId() == null) {
            request.setUserId(Utils.getLoggedinUserId());
        }

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
        BeanUtils.copyProperties(existingData, response);

        if (existingData.getCountryId() != null) {
            CountryResponse countryResponse = countryService.getDataById(existingData.getCountryId());
            response.setCountry(countryResponse);
        }

        if (existingData.getLocationId() != null) {

            LocationResponse thanaResponse = locationService.getDataById(existingData.getLocationId());
            response.setLocation(thanaResponse);

            LocationResponse districtResponse = locationService.getDataById(thanaResponse.getParentId());
            if (districtResponse != null) {
                response.setDistrictId(districtResponse.getId());
                response.setDistrictNameEn(districtResponse.getNameEn());
                response.setDistrictNameBn(districtResponse.getNameBn());

                LocationResponse divisionResponse = locationService.getDataById(districtResponse.getParentId());
                if (divisionResponse != null) {
                    response.setDivisionId(divisionResponse.getId());
                    response.setDivisionNameEn(divisionResponse.getNameEn());
                    response.setDivisionNameBn(divisionResponse.getNameBn());

                    response.setFullAddressEn(
                            response.getHoldingHouseVillage() + ", " + response.getRoadBlockSectorColony() + ", "
                                    + thanaResponse.getNameEn() + ", " + districtResponse.getNameEn() + ", "
                                    + divisionResponse.getNameEn() + "-" + response.getPostCode());

                    response.setFullAddressBn(
                            response.getHoldingHouseVillage() + ", " + response.getRoadBlockSectorColony() + ", "
                                    + thanaResponse.getNameBn() + ", " + districtResponse.getNameBn() + ", "
                                    + divisionResponse.getNameBn() + "-" + response.getPostCode());
                }
            }

        }

        if (existingData.getAddressTypeId() != null) {
            StatusResponse statusResponse = statusService.findStatusById(existingData.getAddressTypeId());
            response.setAddressType(statusResponse);
        }

        return response;
    }

}
