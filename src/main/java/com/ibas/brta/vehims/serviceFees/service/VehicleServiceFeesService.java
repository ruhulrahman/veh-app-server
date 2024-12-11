package com.ibas.brta.vehims.serviceFees.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.common.model.ServiceEconomicCode;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.common.payload.response.ServiceEconomicCodeResponse;
import com.ibas.brta.vehims.common.repository.ServiceEconomicCodeRepository;
import com.ibas.brta.vehims.configurations.model.ServiceEntity;
import com.ibas.brta.vehims.configurations.model.VehicleType;
import com.ibas.brta.vehims.configurations.model.VehicleTypeClassMap;
import com.ibas.brta.vehims.configurations.repository.ServiceRepository;
import com.ibas.brta.vehims.configurations.repository.VehicleTypeRepository;
import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFees;
import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFeesVehicleTypeMap;
import com.ibas.brta.vehims.serviceFees.payload.request.VehicleServiceFeesRequest;
import com.ibas.brta.vehims.serviceFees.payload.request.VehicleServiceFeesSearchFilter;
import com.ibas.brta.vehims.serviceFees.payload.response.VehicleServiceFeesResponse;
import com.ibas.brta.vehims.serviceFees.repository.VehicleServiceFeeRepository;
import com.ibas.brta.vehims.serviceFees.repository.VehicleServiceFeesVehicleTypeMapRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Slf4j
@Service
public class VehicleServiceFeesService {
    @Autowired
    private VehicleServiceFeeRepository vehicleServiceFeeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceEconomicCodeRepository serviceEconomicCodeRepository;

    @Autowired
    private VehicleServiceFeesVehicleTypeMapRepository vehicleTypeFeesMapRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Transactional
    public VehicleServiceFeesResponse createData(VehicleServiceFeesRequest request) {

        VehicleServiceFees requestObject = new VehicleServiceFees();
        BeanUtils.copyProperties(request, requestObject);
        VehicleServiceFees savedData = vehicleServiceFeeRepository.save(requestObject);

        vehicleTypeFeesMapRepository.deleteByServiceFeesId(savedData.getId());

        for (Long vehicleTypeId : request.getVehicleTypeIds()) {
            VehicleServiceFeesVehicleTypeMap vehicleTypeFeesMap = new VehicleServiceFeesVehicleTypeMap();
            vehicleTypeFeesMap.setServiceFeesId(savedData.getId());
            vehicleTypeFeesMap.setVehicleTypeId(vehicleTypeId);
            vehicleTypeFeesMapRepository.save(vehicleTypeFeesMap);
        }

        VehicleServiceFeesResponse response = new VehicleServiceFeesResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    @Transactional
    public VehicleServiceFeesResponse updateData(Long id, VehicleServiceFeesRequest request) {

        Optional<VehicleServiceFees> existingData = vehicleServiceFeeRepository.findById(id);

        if (existingData.isPresent()) {
            VehicleServiceFees requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            VehicleServiceFees updatedData = vehicleServiceFeeRepository.save(requestObject);

            vehicleTypeFeesMapRepository.deleteByServiceFeesId(updatedData.getId());

            for (Long vehicleTypeId : request.getVehicleTypeIds()) {
                VehicleServiceFeesVehicleTypeMap vehicleTypeFeesMap = new VehicleServiceFeesVehicleTypeMap();
                vehicleTypeFeesMap.setServiceFeesId(updatedData.getId());
                vehicleTypeFeesMap.setVehicleTypeId(vehicleTypeId);
                vehicleTypeFeesMapRepository.save(vehicleTypeFeesMap);
            }

            VehicleServiceFeesResponse response = new VehicleServiceFeesResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (vehicleServiceFeeRepository.existsById(id)) {
            vehicleTypeFeesMapRepository.deleteByServiceFeesId(id);
            vehicleServiceFeeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<VehicleServiceFeesResponse> findAllBySearch(VehicleServiceFeesSearchFilter filter) {

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        // Retrieve all records from the database
        Page<VehicleServiceFeesResponse> records = vehicleServiceFeeRepository
                .findListWithPaginationBySearch(
                        filter.getServiceId(),
                        filter.getIsAirCondition(),
                        filter.getIsHire(),
                        filter.getVehicleTypeId(),
                        filter.getIsActive(),
                        pageable);

        // If no records found, return an empty response
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<VehicleServiceFeesResponse> responseData = records.map(record -> {
            VehicleServiceFeesResponse response = new VehicleServiceFeesResponse();
            BeanUtils.copyProperties(record, response);

            List<Long> vehicleTypeIds = vehicleTypeFeesMapRepository.findVehicleTypeIdsByServiceFeesId(record.getId());

            List<VehicleType> vehicleTypes = vehicleTypeRepository
                    .findByIdsIsActiveTrueOrderByNameEnAsc(vehicleTypeIds);

            response.setVehicleTypeIds(vehicleTypeIds);
            response.setVehicleTypes(vehicleTypes);

            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // List all records
    public List<VehicleServiceFeesResponse> getServiceWithFeesByParentServiceCode(String serviceCode) {

        List<Long> serviceIds = serviceRepository.findChildServiceIdsByServiceCode(serviceCode);
        List<VehicleServiceFeesResponse> records = vehicleServiceFeeRepository
                .getServiceWithFeesByServiceIds(serviceIds);

        return records;
    }

    public List<Long> getServicesIds() {
        return vehicleServiceFeeRepository.getServicesIdsIsActiveTrue();
    }

    // Find a single record by ID
    public VehicleServiceFeesResponse getDataById(Long id) {

        VehicleServiceFees existingData = vehicleServiceFeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        VehicleServiceFeesResponse response = new VehicleServiceFeesResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public ServiceEconomicCodeResponse getServiceEconomicCodeByServiceCode(String serviceCode) {
        ServiceEntity service = serviceRepository.findByServiceCode(serviceCode);
        if (service == null) {
            return null;
        }

        Optional<ServiceEconomicCode> serviceEconomicCode = serviceEconomicCodeRepository
                .findById(service.getServiceEconomicCodeId());

        if (serviceEconomicCode.isPresent()) {
            ServiceEconomicCodeResponse response = new ServiceEconomicCodeResponse();
            BeanUtils.copyProperties(serviceEconomicCode.get(), response);
            return response;
        }
        return null;
    }

}
