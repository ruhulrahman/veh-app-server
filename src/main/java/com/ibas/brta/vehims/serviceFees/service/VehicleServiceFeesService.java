package com.ibas.brta.vehims.serviceFees.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.StatusRepository;
import com.ibas.brta.vehims.serviceFees.dao.VehicleServiceFeesDao;
import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFeesRule;
import com.ibas.brta.vehims.serviceFees.payload.response.VehicleSpecificServiceFeesResponse;
import com.ibas.brta.vehims.serviceFees.repository.VehicleServiceFeesRuleRepository;
import com.ibas.brta.vehims.util.Utils;
import com.ibas.brta.vehims.vehicle.model.VServiceRequest;
import com.ibas.brta.vehims.vehicle.model.VehicleInfo;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;
import com.ibas.brta.vehims.vehicle.repository.VehicleInfoRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @Autowired
    VServiceRequestRepository serviceRequestRepository;

    @Autowired
    VehicleInfoRepository vehicleInfoRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    VehicleServiceFeesRuleRepository vehicleServiceFeesRuleRepository;

    @Autowired
    VehicleServiceFeesDao vehicleServiceFeesDao;

    EntityManager entityManager;

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

        createVehicleServiceFeesRules(savedData, request.getVehicleTypeIds());

        VehicleServiceFeesResponse response = new VehicleServiceFeesResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    @Transactional
    public void createVehicleServiceFeesRules(VehicleServiceFees savedData, List<Long> vehicleTypeIds) {

        List<Long> statusIds = new ArrayList<Long>();

        if (savedData.getIsAirCondition()) {
            Status status = statusRepository.findByStatusCode("air_conditioned");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (!vehicleTypeIds.isEmpty()) {
            Status status = statusRepository.findByStatusCode("vehicle_types");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (savedData.getIsHire()) {
            Status status = statusRepository.findByStatusCode("hire");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (savedData.getIsElectricVehicle()) {
            Status status = statusRepository.findByStatusCode("electric_vehicle");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (Utils.notNullOrEmpty(savedData.getCcMin())  || Utils.notNullOrEmpty(savedData.getCcMax())) {
            Status status = statusRepository.findByStatusCode("cc");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (Utils.notNullOrEmpty(savedData.getKwMin())  || Utils.notNullOrEmpty(savedData.getKwMax())) {
            Status status = statusRepository.findByStatusCode("kw");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (Utils.notNullOrEmpty(savedData.getSeatMin())  || Utils.notNullOrEmpty(savedData.getSeatMax())) {
            Status status = statusRepository.findByStatusCode("seat");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (Utils.notNullOrEmpty(savedData.getWeightMin())  || Utils.notNullOrEmpty(savedData.getWeightMax())) {
            Status status = statusRepository.findByStatusCode("weight");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

        if (savedData.getIsApplicableForMultipleVehicleOwner()) {
            Status status = statusRepository.findByStatusCode("multiple_vehicle_owner");
            if (status != null) {
                statusIds.add(status.getId());
            }
        }

//        vehicleServiceFeesRuleRepository.deleteByServiceFeesId(savedData.getId());

        for (Long vehicleTypeId : vehicleTypeIds) {
            VehicleServiceFeesRule vehicleServiceFeesRule = new VehicleServiceFeesRule();
            vehicleServiceFeesRule.setServiceFeesId(savedData.getId());
            vehicleServiceFeesRule.setServiceId(savedData.getServiceId());
            vehicleServiceFeesRule.setVehicleTypeId(vehicleTypeId);
            vehicleServiceFeesRule.setStatusIds(statusIds);

            vehicleServiceFeesRuleRepository.save(vehicleServiceFeesRule);
        }
    }

    // Update operation
    @Transactional
    public VehicleServiceFeesResponse updateData(Long id, VehicleServiceFeesRequest request) {

        Optional<VehicleServiceFees> existingData = vehicleServiceFeeRepository.findById(id);

        if (existingData.isPresent()) {
            existingData.get().setIsActive(false);
            vehicleServiceFeeRepository.save(existingData.get());

            VehicleServiceFees requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject, "id");

            VehicleServiceFees updatedData = vehicleServiceFeeRepository.save(requestObject);

            vehicleTypeFeesMapRepository.deleteByServiceFeesId(updatedData.getId());

            for (Long vehicleTypeId : request.getVehicleTypeIds()) {
                VehicleServiceFeesVehicleTypeMap vehicleTypeFeesMap = new VehicleServiceFeesVehicleTypeMap();
                vehicleTypeFeesMap.setServiceFeesId(updatedData.getId());
                vehicleTypeFeesMap.setVehicleTypeId(vehicleTypeId);
                vehicleTypeFeesMapRepository.save(vehicleTypeFeesMap);
            }

            createVehicleServiceFeesRules(updatedData, request.getVehicleTypeIds());

            VehicleServiceFeesResponse response = new VehicleServiceFeesResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    @Transactional
    public void generateVehicleServiceRules() {

        List<VehicleServiceFees> serviceFeesList = vehicleServiceFeeRepository.findAll();

//        log.info("serviceFeesList ============================= {}", serviceFeesList);
//        log.info("serviceFeesList.length ============================= {}", serviceFeesList.size());

        if (!serviceFeesList.isEmpty()) {

            for (VehicleServiceFees vehicleServiceFees: serviceFeesList) {
//                List<Long>  vehicleTypeIds = vehicleServiceFeesRuleRepository.findVehicleTypeIdsByServiceFeesId(vehicleServiceFees.getId());
//                log.info("vServiceFeesId ============================= {}", vehicleServiceFees.getId());
                List<Long> vehicleTypeIds = vehicleTypeFeesMapRepository.findVehicleTypeIdsByServiceFeesId(vehicleServiceFees.getId());
//                log.info("vehicleTypeIds ============================= {}", vehicleTypeIds);
                createVehicleServiceFeesRules(vehicleServiceFees, vehicleTypeIds);
            }

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

//        log.info("filter ============== {}", filter);
        // Retrieve all records from the database
        Page<VehicleServiceFeesResponse> records = vehicleServiceFeeRepository
                .findListWithPaginationBySearch(
                        filter.getServiceId(),
                        filter.getIsAirCondition(),
                        filter.getIsElectricVehicle(),
                        filter.getIsHire(),
                        filter.getIsApplicableForMultipleVehicleOwner(),
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

    // List all records
    public List<VehicleServiceFeesResponse> getServiceWithFeesByServiceRequestId(Long serviceRequestId,
            String serviceCode) {

        Optional<VServiceRequest> serviceRequest = serviceRequestRepository.findById(serviceRequestId);

        if (serviceRequest.isEmpty()) {
            throw new EntityNotFoundException("Service Request not found with id: " + serviceRequestId);
        }

        Optional<VehicleInfo> vehicleInfo = vehicleInfoRepository.findById(serviceRequest.get().getVehicleInfoId());

        if (vehicleInfo.isEmpty()) {
            throw new EntityNotFoundException("Vehicle not found with id: " + serviceRequest.get().getVehicleInfoId());
        }

        List<Long> serviceIds = serviceRepository.findChildServiceIdsByServiceCode(serviceCode);

        List<VehicleServiceFeesResponse> records = vehicleServiceFeeRepository
                .getServiceWithFeesByServiceIds(serviceIds);

        return records;
    }
    // List all records
    public List<VehicleSpecificServiceFeesResponse> getServiceWithFeesByServiceRequestIdAndServiceCode(Long serviceRequestId,
            String serviceCode) {

        Optional<VServiceRequest> serviceRequest = serviceRequestRepository.findById(serviceRequestId);

        if (serviceRequest.isEmpty()) {
            throw new EntityNotFoundException("Service Request not found with id: " + serviceRequestId);
        }

        Optional<VehicleInfo> vehicleInfo = vehicleInfoRepository.findById(serviceRequest.get().getVehicleInfoId());

        if (vehicleInfo.isEmpty()) {
            throw new EntityNotFoundException("Vehicle not found with id: " + serviceRequest.get().getVehicleInfoId());
        }

        List<Long> serviceIds = serviceRepository.findChildServiceIdsByServiceCode(serviceCode);
//        log.info("serviceIds ============== {}", serviceIds);
        List<ServiceEntity> services = serviceRepository.getServicesByServiceIds(serviceIds);

//        log.info("services ============== {}", services);

        List<VehicleSpecificServiceFeesResponse> vechileServiceFeesList = services.stream().map(item -> {
            VehicleSpecificServiceFeesResponse response = new VehicleSpecificServiceFeesResponse();
            BeanUtils.copyProperties(item, response);
            response.setServiceId(item.getId());
            response.setServiceNameEn(item.getNameEn());
            response.setServiceNameBn(item.getNameBn());

            VehicleServiceFees vServiceFees;

//            if (vehicleInfo.get().getIsElectrictVehicle()) {
//                vServiceFees = vehicleServiceFeeRepository.findFeesForElectricVehicle(
//                        item.getId(), vehicleInfo.get().getCcOrKw(),
//                        vehicleInfo.get().getIsAirConditioner(), vehicleInfo.get().getIsHire());
//            } else {
//                vServiceFees = vehicleServiceFeeRepository.findFeesForNotElectricVehicle(
//                        item.getId(), vehicleInfo.get().getCcOrKw(),
//                        vehicleInfo.get().getTotalSeat(), vehicleInfo.get().getMaxLadenWeight(),
//                        vehicleInfo.get().getIsAirConditioner(), vehicleInfo.get().getIsHire());
//            }

//            if (item.getServiceCode().equals("fitness_certificate_fee")
//                    || item.getServiceCode().equals("digital_registration_certificate_fee")
//                    || item.getServiceCode().equals("label_for_fitness")
//                    || item.getServiceCode().equals("label_for_road_tax")
//                    || item.getServiceCode().equals("rfid_and_one_number_plate__type_3b")
//            ) {
//                vServiceFees = vehicleServiceFeeRepository.findByServiceId(item.getId());
//            }


//            if (item.getServiceCode().equals("vehicle_registration_fee")) {
//                vServiceFees = vehicleServiceFeeRepository.findFeesForVehicleRegistration(
//                        item.getId(), vehicleInfo.get().getVehicleTypeId(), vehicleInfo.get().getIsElectrictVehicle(),vehicleInfo.get().getCcOrKw(),
//                        vehicleInfo.get().getTotalSeat(), vehicleInfo.get().getMaxLadenWeight(),
//                        vehicleInfo.get().getIsAirConditioner(), vehicleInfo.get().getIsHire());
//            }

//            log.info("serviceId ========== {}", item.getId());
//            log.info("vehicleTypeId ========== {}", vehicleInfo.get().getVehicleTypeId());

            List<Long> statusIds = vehicleServiceFeesRuleRepository.getStatusIdsByServiceIdAndVehicleTypeId(item.getId(), vehicleInfo.get().getVehicleTypeId());

            List<String> statusCodes = statusRepository.getStatusCodesByIds(statusIds);

//            log.info("statusCodes ============== {}", statusCodes);

            if (!statusCodes.isEmpty()) {
                    vServiceFees = vehicleServiceFeesDao.findFirstVehicleServiceFees(statusCodes,
                            item.getId(), vehicleInfo.get().getVehicleTypeId(), vehicleInfo.get().getIsElectrictVehicle(),vehicleInfo.get().getCcOrKw(),
                            vehicleInfo.get().getTotalSeat(), vehicleInfo.get().getMaxLadenWeight(),
                            vehicleInfo.get().getIsAirConditioner(), vehicleInfo.get().getIsHire());
            } else {
                vServiceFees = vehicleServiceFeeRepository.findByServiceId(item.getId());
            }

//            log.info("serviceId ================ {}", item.getId());
//            log.info("getVehicleTypeId ================ {}", vehicleInfo.get().getVehicleTypeId());
//            log.info("getIsElectrictVehicle ================ {}", vehicleInfo.get().getIsElectrictVehicle());
//            log.info("getIsAirConditioner ================ {}", vehicleInfo.get().getIsAirConditioner());
//            log.info("getIsHire ================ {}", vehicleInfo.get().getIsHire());
//            log.info("getCcOrKw ================ {}", vehicleInfo.get().getCcOrKw());
//            log.info("getTotalSeat ================ {}", vehicleInfo.get().getTotalSeat());
//            log.info("getMaxLadenWeight ================ {}", vehicleInfo.get().getMaxLadenWeight());
//            log.info("vServiceFees ================ {}", vServiceFees);

            if (vServiceFees != null) {
                response.setServiceFee(vServiceFees.getMainFee());
            }

            return response;
        }).collect(Collectors.toList());

//                List<VehicleServiceFeesResponse> records = vehicleServiceFeeRepository
//                .getServiceWithFeesByServiceIdsAndVehicleInfoParams(serviceIds, vehicleInfo.get().getCcOrKw(),
//                        vehicleInfo.get().getTotalSeat(), vehicleInfo.get().getMaxLadenWeight(), vehicleInfo.get().getCcOrKw(), vehicleInfo.get().getIsAirConditioner(), vehicleInfo.get().getIsHire());

        return vechileServiceFeesList;
    }

//    public VehicleServiceFees getUsers(Long designationId) {
//        QVehicleServiceFees user = QVehicleServiceFees.user;
//        ArrayBuilders.BooleanBuilder builder = new BooleanBuilder();
//
//        if (designationId != null) {
//            builder.and(user.designationId.eq(designationId));
//        }
//
//        return (VehicleServiceFees) vehicleServiceFeeRepository.findOne(builder);
//    }

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
