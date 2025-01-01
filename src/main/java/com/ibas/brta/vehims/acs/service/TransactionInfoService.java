package com.ibas.brta.vehims.acs.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.acs.IService.ITransactionInfo;
import com.ibas.brta.vehims.acs.model.TransactionInfo;
import com.ibas.brta.vehims.acs.repository.PaymentDetailsRepository;
import com.ibas.brta.vehims.acs.repository.TransactionInfoRepository;
import com.ibas.brta.vehims.configurations.model.ServiceEntity;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.ServiceRepository;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.model.LearnerLicense;
import com.ibas.brta.vehims.drivingLicense.payload.request.LearnerLicenseRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.LearnerLicenseResponse;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceRequestRepository;
import com.ibas.brta.vehims.drivingLicense.repository.LearnerLicenseRepository;
import com.ibas.brta.vehims.drivingLicense.service.DrivingLicenseService;
import com.ibas.brta.vehims.drivingLicense.service.LearnerLicenseService;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.util.Utils;
import com.ibas.brta.vehims.vehicle.model.VServiceRequest;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;

/**
 * @author ashshakur.rahaman
 */
@Service
public class TransactionInfoService implements ITransactionInfo {

    @Autowired
    TransactionInfoRepository repository;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private DLServiceRequestRepository dlServiceRequestRepository;

    @Autowired
    private VServiceRequestRepository vServiceRequestRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private DrivingLicenseService drivingLicenseService;

    @Autowired
    private LearnerLicenseService learnerLicenseService;

    @Autowired
    private LearnerLicenseRepository learnerLicenseRepository;

    public List<TransactionInfo> findAll() {
        return repository.findAll();
    }

    public Optional<TransactionInfo> findById(Long id) {
        return repository.findById(id);
    }

    public TransactionInfo save(TransactionInfo transactionInfo) {
        return repository.save(transactionInfo);
    }

    public Optional<TransactionInfo> findByTransactionId(String transactionId) {
        return repository.findByTransactionIdEquals(transactionId);
    }

    public void paymentUpdateForDrivingLicense(TransactionInfo transactionInfo) {

        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(transactionInfo.getServiceId());

        if (serviceEntity.isPresent()) {

            if (serviceEntity.get().getServiceCode().equals("before_driving_skills_test_fees")) {
                paymentForLearnerDrivingLicense(transactionInfo);
            }

            if (serviceEntity.get().getServiceCode().equals("after_driving_skills_test_fees")) {
                paymentForNewDrivingLicense(transactionInfo);
            }
        }
    }

    public void paymentForLearnerDrivingLicense(TransactionInfo transactionInfo) {

        DLServiceRequest dlServiceRequest = dlServiceRequestRepository
                .findByServiceRequestNo(transactionInfo.getServiceRequestNo());

        StatusProjection statusPending = commonRepository
                .getStatusByStatusCodeOrId("dl_app_pending");
        dlServiceRequest.setApplicationStatusId(statusPending.getId());
        dlServiceRequest.setApplicationDate(LocalDateTime.now());
        dlServiceRequest.setIsLearnerFeePaid(true);

        LearnerLicenseResponse existLearner = learnerLicenseService
                .getDataByServiceRequestId(dlServiceRequest.getId());

        if (existLearner != null) {

            LearnerLicenseRequest learnerLicenseRequest = new LearnerLicenseRequest();
            BeanUtils.copyProperties(existLearner, learnerLicenseRequest);

            learnerLicenseRequest.setDlServiceRequestId(dlServiceRequest.getId());
            learnerLicenseRequest.setLearnerNumber(Utils.generateLearnerNumber());
            learnerLicenseRequest.setIssueDate(LocalDateTime.now());
            learnerLicenseRequest.setExamDate(LocalDateTime.now().plusMonths(3));
            learnerLicenseRequest.setExpireDate(LocalDateTime.now().plusMonths(6));

            LearnerLicense learnerLicense = learnerLicenseRepository
                    .findByDlServiceRequestId(dlServiceRequest.getId());

            Integer totalLearner = learnerLicenseRepository.countByExamDateAndExamVenueId(
                    learnerLicenseRequest.getExamDate(),
                    learnerLicense.getExamVenueId());

            if (totalLearner <= 600) {

                LearnerLicenseResponse learnerLicenseResponse = learnerLicenseService
                        .getDataByExamDateAndVenue(
                                learnerLicenseRequest.getExamDate(),
                                learnerLicense.getExamVenueId());

                if (learnerLicenseResponse != null) {
                    learnerLicenseRequest.setRollNo(learnerLicenseResponse.getRollNo() + 1);
                } else {
                    learnerLicenseRequest.setRollNo(1);
                }
            } else {

                learnerLicenseRequest.setExamDate(learnerLicenseRequest.getExamDate().plusDays(1));
                learnerLicenseRequest
                        .setExpireDate(learnerLicenseRequest.getExpireDate().plusDays(1));

                LearnerLicenseResponse learnerLicenseResponse = learnerLicenseService
                        .getDataByExamDateAndVenue(
                                learnerLicenseRequest.getExamDate(),
                                learnerLicense.getExamVenueId());

                if (learnerLicenseResponse != null) {
                    learnerLicenseRequest.setRollNo(learnerLicenseResponse.getRollNo() + 1);
                } else {
                    learnerLicenseRequest.setRollNo(1);
                }
            }
            learnerLicenseRequest.setIsPaid(true);

            learnerLicenseService.updateData(existLearner.getId(), learnerLicenseRequest);

        }

        dlServiceRequestRepository.save(dlServiceRequest);

    }

    public void paymentForNewDrivingLicense(TransactionInfo transactionInfo) {

        DLServiceRequest dlServiceRequest = dlServiceRequestRepository
                .findByServiceRequestNo(transactionInfo.getServiceRequestNo());
        StatusProjection statusPending = commonRepository
                .getStatusByStatusCodeOrId("dl_app_final_submitted");
        dlServiceRequest.setApplicationStatusId(statusPending.getId());
        dlServiceRequest.setIsLicenseFeePaid(true);
        dlServiceRequestRepository.save(dlServiceRequest);

    }

    public void paymentUpdateForVehicle(TransactionInfo transactionInfo) {

        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(transactionInfo.getServiceId());

        if (serviceEntity.isPresent()) {

            if (serviceEntity.get().getServiceCode().equals("vehicle_registration_related_primary_fees")) {
                paymentForNewVehicleRegistrationInspection(transactionInfo);
            }

            if (serviceEntity.get().getServiceCode().equals("vehicle_registration_related_final_fees")) {
                paymentForNewVehicleRegistration(transactionInfo);
            }
        }
    }

    public void paymentForNewVehicleRegistrationInspection(TransactionInfo transactionInfo) {

        Optional<VServiceRequest> vServiceRequest = vServiceRequestRepository
                .findByServiceRequestNo(transactionInfo.getServiceRequestNo());

        VServiceRequest serviceRequest = new VServiceRequest();

        if (vServiceRequest.isPresent()) {
            BeanUtils.copyProperties(vServiceRequest.get(), serviceRequest);
        }

        StatusProjection statusPending = commonRepository
                .getStatusByStatusCodeOrId("vehicle_app_pending");

        serviceRequest.setApplicationStatusId(statusPending.getId());
        serviceRequest.setApplicationDate(LocalDateTime.now());
        serviceRequest.setIsPaidForInspection(true);
        // serviceRequest.setIsPaidForFinalSubmission(true);

        vServiceRequestRepository.save(serviceRequest);

    }

    public void paymentForNewVehicleRegistration(TransactionInfo transactionInfo) {

        Optional<VServiceRequest> vServiceRequest = vServiceRequestRepository
                .findByServiceRequestNo(transactionInfo.getServiceRequestNo());

        VServiceRequest serviceRequest = new VServiceRequest();

        if (vServiceRequest.isPresent()) {
            BeanUtils.copyProperties(vServiceRequest.get(), serviceRequest);
        }

        StatusProjection statusPending = commonRepository
                .getStatusByStatusCodeOrId("vehicle_app_final_submitted");

        serviceRequest.setApplicationStatusId(statusPending.getId());
        serviceRequest.setApplicationDate(LocalDateTime.now());
        serviceRequest.setIsPaidForFinalSubmission(true);
        serviceRequest.setIsPaid(true);

        vServiceRequestRepository.save(serviceRequest);

    }
}
