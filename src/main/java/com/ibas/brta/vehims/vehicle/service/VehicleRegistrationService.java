package com.ibas.brta.vehims.vehicle.service;

import com.ibas.brta.vehims.configurations.model.*;
import com.ibas.brta.vehims.configurations.payload.response.*;
import com.ibas.brta.vehims.configurations.repository.*;
import com.ibas.brta.vehims.configurations.service.CountryService;
import com.ibas.brta.vehims.vehicle.model.VehicleJointOwner;
import com.ibas.brta.vehims.vehicle.model.VehicleRegistration;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegistrationRequest;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegistrationRequest;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleJointOwnerResponse;
import com.ibas.brta.vehims.vehicle.payload.response.VehicleRegistrationResponse;
import com.ibas.brta.vehims.vehicle.repository.VehicleJointOwnerRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleOwnerRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleRegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VehicleRegistrationService {

    @Autowired
    VehicleRegistrationRepository vehicleRegistrationRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    VehicleClassRepository vehicleClassRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    VehicleRegistrationMarkRepository vehicleRegistrationMarkRepository;

    @Autowired
    VehicleOwnerRepository vehicleOwnerRepository;


    // Create or Insert operation
    public VehicleRegistrationResponse createData(VehicleRegistrationRequest request) {

        VehicleRegistration requestObject = new VehicleRegistration();
        BeanUtils.copyProperties(request, requestObject);
        VehicleRegistration savedData = vehicleRegistrationRepository.save(requestObject);

        VehicleRegistrationResponse response = new VehicleRegistrationResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public VehicleRegistrationResponse updateData(Long id, VehicleRegistrationRequest request) {

        Optional<VehicleRegistration> existingData = vehicleRegistrationRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleRegistration requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleRegistration updatedData = vehicleRegistrationRepository.save(requestObject);

            VehicleRegistrationResponse response = new VehicleRegistrationResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Find a single record by ID
    public VehicleRegistrationResponse getDataById(Long id) {

        VehicleRegistration existingData = vehicleRegistrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleRegistrationResponse response = new VehicleRegistrationResponse();

        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public void deleteById(Long id) {
        vehicleRegistrationRepository.deleteById(id);
    }

    public VehicleRegistrationResponse getFullDataByVehicleInfoId(Long vehicleInfoId) {

        Optional<VehicleRegistration> vehicleRegistration = vehicleRegistrationRepository.findByVehicleInfoId(vehicleInfoId);

        VehicleRegistrationResponse response = new VehicleRegistrationResponse();

        BeanUtils.copyProperties(vehicleRegistration, response);

        if (response.getVehicleRegistrationMarkId() != null) {
            Optional<VehicleRegistrationMark> vehicleRegistrationMark = vehicleRegistrationMarkRepository.findById(response.getVehicleRegistrationMarkId());
            if (vehicleRegistrationMark.isPresent()) {
                VehicleRegistrationMarkResponse vehicleRegistrationMarkResponse = new VehicleRegistrationMarkResponse();
                BeanUtils.copyProperties(vehicleRegistrationMark.get(), vehicleRegistrationMarkResponse);
                response.setVehicleRegistrationMark(vehicleRegistrationMarkResponse);
            }
        }

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

        if (response.getRegOfficeId() != null) {
            Optional<Organization> organization = organizationRepository.findById(response.getRegOfficeId());
            if (organization.isPresent()) {
                OrganizationResponse organizationResponse = new OrganizationResponse();
                BeanUtils.copyProperties(organization, organizationResponse);
                response.setRegOffice(organizationResponse);
            }
        }

        return response;
    }


}
