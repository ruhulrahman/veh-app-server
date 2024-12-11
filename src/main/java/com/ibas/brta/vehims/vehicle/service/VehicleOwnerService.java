package com.ibas.brta.vehims.vehicle.service;

import java.util.Optional;

import com.ibas.brta.vehims.configurations.service.CountryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibas.brta.vehims.vehicle.model.VehicleOwner;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleOwnerRequest;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleOwnerResponse;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleInfoRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleOwnerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleOwnerService {

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

    // Create or Insert operation
    public VehicleOwnerResponse createData(VehicleOwnerRequest request) {

        VehicleOwner requestObject = new VehicleOwner();
        BeanUtils.copyProperties(request, requestObject);
        VehicleOwner savedData = vehicleOwnerRepository.save(requestObject);

        VehicleOwnerResponse response = new VehicleOwnerResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleOwnerResponse updateData(Long id, VehicleOwnerRequest request) {

        Optional<VehicleOwner> existingData = vehicleOwnerRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleOwner requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleOwner updatedData = vehicleOwnerRepository.save(requestObject);

            VehicleOwnerResponse response = new VehicleOwnerResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Find a single record by ID
    public VehicleOwnerResponse getDataById(Long id) {

        VehicleOwner existingData = vehicleOwnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleOwnerResponse response = new VehicleOwnerResponse();

        BeanUtils.copyProperties(existingData, response);

        return response;
    }

}
