package com.ibas.brta.vehims.vehicle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibas.brta.vehims.configurations.model.Location;
import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.configurations.repository.LocationRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
import com.ibas.brta.vehims.configurations.service.CountryService;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.vehicle.model.VehicleJointOwner;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleJointOwnerRequest;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleJointOwnerResponse;
import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import com.ibas.brta.vehims.vehicle.repository.VehicleJointOwnerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleJointOwnerService {

    @Autowired
    CountryService countryService;

    @Autowired
    VehicleJointOwnerRepository vehicleJointOwnerRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    StatusRepository statusRepository;

    // Create or Insert operation
    public VehicleJointOwnerResponse createData(VehicleJointOwnerRequest request) {

        VehicleJointOwner requestObject = new VehicleJointOwner();
        BeanUtils.copyProperties(request, requestObject);
        VehicleJointOwner savedData = vehicleJointOwnerRepository.save(requestObject);

        VehicleJointOwnerResponse response = new VehicleJointOwnerResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleJointOwnerResponse updateData(Long id, VehicleJointOwnerRequest request) {

        Optional<VehicleJointOwner> existingData = vehicleJointOwnerRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleJointOwner requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleJointOwner updatedData = vehicleJointOwnerRepository.save(requestObject);

            VehicleJointOwnerResponse response = new VehicleJointOwnerResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Find a single record by ID
    public VehicleJointOwnerResponse getDataById(Long id) {

        VehicleJointOwner existingData = vehicleJointOwnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleJointOwnerResponse response = new VehicleJointOwnerResponse();

        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public List<VehicleJointOwnerResponse> getJointOwnersByVehicleInfoId(Long vehicleInfoId) {

        List<VehicleJointOwner> existingData = vehicleJointOwnerRepository.findByVehicleInfoId(vehicleInfoId);

        if (existingData.isEmpty()) {
            return new ArrayList<>();
        } else {
            return mappedVehicleJointOwners(existingData);
        }
    }

    public List<VehicleJointOwnerResponse> getJointOwnersByVehicleInfoIdAndServiceRequestId(Long vehicleInfoId,
            Long serviceRequestId) {

        List<VehicleJointOwner> existingData = vehicleJointOwnerRepository
                .findByVehicleInfoIdAndServiceRequestId(vehicleInfoId, serviceRequestId);

        if (existingData.isEmpty()) {
            return new ArrayList<>();
        } else {
            return mappedVehicleJointOwners(existingData);
        }
    }

    public List<VehicleJointOwnerResponse> getJointOwnersByVehicleOwnerId(Long vehicleOwnerid) {

        List<VehicleJointOwner> existingData = vehicleJointOwnerRepository
                .findByVehicleOwnerId(vehicleOwnerid);

        if (existingData.isEmpty()) {
            return new ArrayList<>();
        } else {
            return mappedVehicleJointOwners(existingData);
        }
    }

    @Transactional
    public void deleteByVehicleOwnerId(Long vehicleOwnerid) {
        vehicleJointOwnerRepository.deleteByVehicleOwnerId(vehicleOwnerid);
    }

    public List<VehicleJointOwnerResponse> mappedVehicleJointOwners(List<VehicleJointOwner> existingData) {

        if (existingData == null) {
            return null;
        }

        return existingData.stream().map(item -> {
            VehicleJointOwnerResponse response = new VehicleJointOwnerResponse();
            BeanUtils.copyProperties(item, response);

            if (item.getCountryId() != null) {
                CountryResponse countryResponse = countryService.getDataById(item.getCountryId());
                response.setCountry(countryResponse);
            }

            if (item.getLocationId() != null) {

                LocationResponse thanaResponse = getLocationById(item.getLocationId());
                response.setLocation(thanaResponse);

                LocationResponse districtResponse = getLocationById(thanaResponse.getParentId());
                if (districtResponse != null) {
                    response.setDistrictId(districtResponse.getId());
                    response.setDistrictNameEn(districtResponse.getNameEn());
                    response.setDistrictNameBn(districtResponse.getNameBn());

                    LocationResponse divisionResponse = getLocationById(districtResponse.getParentId());
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

            if (item.getAddressTypeId() != null) {
                StatusResponse statusResponse = getStatusById(item.getAddressTypeId());
                response.setAddressType(statusResponse);
            }

            return response;
        }).collect(Collectors.toList());

    }

    // Find a single record by ID
    public LocationResponse getLocationById(Long id) {

        Location existingData = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        LocationResponse response = new LocationResponse();
        BeanUtils.copyProperties(existingData, response);

        StatusResponse statusResponse = getStatusById(existingData.getLocationTypeId());

        if (statusResponse != null) {
            response.setLocationTypeNameEn(statusResponse.getNameEn());
            response.setLocationTypeNameBn(statusResponse.getNameBn());
        }

        if (existingData.getParentId() != null) {

            Optional<Location> location = locationRepository.findById(existingData.getParentId());

            if (location != null) {
                LocationResponse locationResponse = new LocationResponse();
                BeanUtils.copyProperties(location.get(), locationResponse);
                response.setParentLocation(locationResponse);
            }
        }

        return response;
    }


    public StatusResponse getStatusById(Long id) {
        Optional<Status> existingData = statusRepository.findById(id);

        if (!existingData.isPresent()) {
            return null;
        }

        StatusResponse response = new StatusResponse();
        BeanUtils.copyProperties(existingData, response);
        return response;
    }

}
