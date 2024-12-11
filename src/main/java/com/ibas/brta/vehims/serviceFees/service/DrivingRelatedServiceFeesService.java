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
import com.ibas.brta.vehims.configurations.repository.ServiceRepository;
import com.ibas.brta.vehims.serviceFees.model.DrivingRelatedServiceFees;
import com.ibas.brta.vehims.serviceFees.payload.request.DrivingRelatedServiceFeesRequest;
import com.ibas.brta.vehims.serviceFees.payload.response.DrivingRelatedServiceFeesResponse;
import com.ibas.brta.vehims.serviceFees.repository.DrivingRelatedServiceFeesRepository;

import jakarta.persistence.EntityNotFoundException;

@Slf4j
@Service
public class DrivingRelatedServiceFeesService {
    @Autowired
    private DrivingRelatedServiceFeesRepository drivingRelatedServiceFeesRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceEconomicCodeRepository serviceEconomicCodeRepository;

    public DrivingRelatedServiceFeesResponse createData(DrivingRelatedServiceFeesRequest request) {

        DrivingRelatedServiceFees requestObject = new DrivingRelatedServiceFees();
        BeanUtils.copyProperties(request, requestObject);
        DrivingRelatedServiceFees savedData = drivingRelatedServiceFeesRepository.save(requestObject);

        DrivingRelatedServiceFeesResponse response = new DrivingRelatedServiceFeesResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public DrivingRelatedServiceFeesResponse updateData(Long id, DrivingRelatedServiceFeesRequest request) {

        Optional<DrivingRelatedServiceFees> existingData = drivingRelatedServiceFeesRepository.findById(id);

        if (existingData.isPresent()) {
            DrivingRelatedServiceFees requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            DrivingRelatedServiceFees updatedData = drivingRelatedServiceFeesRepository.save(requestObject);

            DrivingRelatedServiceFeesResponse response = new DrivingRelatedServiceFeesResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (drivingRelatedServiceFeesRepository.existsById(id)) {
            drivingRelatedServiceFeesRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<DrivingRelatedServiceFeesResponse> findAllBySearch(String serviceNameEn, Boolean isActive,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<DrivingRelatedServiceFeesResponse> records = drivingRelatedServiceFeesRepository
                .findListWithPaginationBySearch(
                        serviceNameEn,
                        isActive,
                        pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<DrivingRelatedServiceFeesResponse> responseData = records.map(record -> {
            DrivingRelatedServiceFeesResponse response = new DrivingRelatedServiceFeesResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // List all records
    public List<DrivingRelatedServiceFeesResponse> getServiceWithFeesByParentServiceCode(String serviceCode) {

        List<Long> serviceIds = serviceRepository.findChildServiceIdsByServiceCode(serviceCode);
        List<DrivingRelatedServiceFeesResponse> records = drivingRelatedServiceFeesRepository
                .getServiceWithFeesByServiceIds(serviceIds);

        return records;
    }

    public List<Long> getServicesIds() {
        return drivingRelatedServiceFeesRepository.getServicesIdsIsActiveTrue();
    }

    // Find a single record by ID
    public DrivingRelatedServiceFeesResponse getDataById(Long id) {

        DrivingRelatedServiceFees existingData = drivingRelatedServiceFeesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        DrivingRelatedServiceFeesResponse response = new DrivingRelatedServiceFeesResponse();
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
