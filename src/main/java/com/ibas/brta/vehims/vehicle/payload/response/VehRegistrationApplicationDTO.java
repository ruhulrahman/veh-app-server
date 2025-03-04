package com.ibas.brta.vehims.vehicle.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class VehRegistrationApplicationDTO {
    private Long sl;
    private Long serviceRequestId;
    private String serviceRequestNo;
    private Long vehicleInfoId;
    private Long vehicleClassId;
    private String vehicleClassName;
    private String chassisNumber;
    private String engineNumber;
    private Long ccOrKw;
    private Long manufacturingYear;
    private Long applicantId;
    private Instant applicationDate;
    private Long applicationStatusId;
    private String applicationStatusName;
    private String applicationStatusColor;
    private String applicationStatusCode;
    private Long userId;
    private String nid;
    private String mobile;
    private LocalDateTime forwardDateForInspection;
    private Long inspectorId;
    private Long inspectionStatusId;
    private String inspectionRemarks;
    private LocalDateTime inspectionDate;
    private LocalDateTime forwardDateForRevenue;
    private Long revenueCheckerId;
    private Long revenueStatusId;
    private String revenueRemarks;
    private LocalDateTime revenueCheckDate;
    private Long approvalId;
    private String approvalRemarks;
    private LocalDateTime approvalDate;
    private LocalDateTime rejectionDate;


    // Constructor for JPQL constructor expression
    public VehRegistrationApplicationDTO(
            Long sl,
            Long serviceRequestId,
            String serviceRequestNo,
            Long vehicleInfoId,
            Long vehicleClassId,
            String vehicleClassName,
            String chassisNumber,
            String engineNumber,
            Long ccOrKw,
            Long manufacturingYear,
            Long applicantId,
            Instant applicationDate,
            Long applicationStatusId,
            String applicationStatusName,
            String applicationStatusColor,
            String applicationStatusCode,
            Long userId,
            String nid,
            String mobile,
            LocalDateTime forwardDateForInspection,
            Long inspectorId,
            Long inspectionStatusId,
            String inspectionRemarks,
            LocalDateTime inspectionDate,
            LocalDateTime forwardDateForRevenue,
            Long revenueCheckerId,
            Long revenueStatusId,
            String revenueRemarks,
            LocalDateTime revenueCheckDate,
            Long approvalId,
            String approvalRemarks,
            LocalDateTime approvalDate,
            LocalDateTime rejectionDate) {
        this.sl = sl;
        this.serviceRequestId = serviceRequestId;
        this.serviceRequestNo = serviceRequestNo;
        this.vehicleInfoId = vehicleInfoId;
        this.vehicleClassId = vehicleClassId;
        this.vehicleClassName = vehicleClassName;
        this.chassisNumber = chassisNumber;
        this.engineNumber = engineNumber;
        this.ccOrKw = ccOrKw;
        this.manufacturingYear = manufacturingYear;
        this.applicantId = applicantId;
        this.applicationDate = applicationDate;
        this.applicationStatusId = applicationStatusId;
        this.applicationStatusName = applicationStatusName;
        this.applicationStatusColor = applicationStatusColor;
        this.applicationStatusCode = applicationStatusCode;
        this.userId = userId;
        this.nid = nid;
        this.mobile = mobile;
        this.forwardDateForInspection = forwardDateForInspection;
        this.inspectorId = inspectorId;
        this.inspectionStatusId = inspectionStatusId;
        this.inspectionRemarks = inspectionRemarks;
        this.inspectionDate = inspectionDate;
        this.forwardDateForRevenue = forwardDateForRevenue;
        this.revenueCheckerId = revenueCheckerId;
        this.revenueStatusId = revenueStatusId;
        this.revenueRemarks = revenueRemarks;
        this.revenueCheckDate = revenueCheckDate;
        this.approvalId = approvalId;
        this.approvalRemarks = approvalRemarks;
        this.approvalDate = approvalDate;
        this.rejectionDate = rejectionDate;
    }
}
