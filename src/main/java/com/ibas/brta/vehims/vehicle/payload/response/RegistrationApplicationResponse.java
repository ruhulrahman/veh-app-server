package com.ibas.brta.vehims.vehicle.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author ashshakur.rahaman
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationApplicationResponse {
    Long sl;
    Long serviceRequestId;
    String serviceRequestNo;
    Long vehicleInfoId;
    Long vehicleClassId;
    String vehicleClassName;
    String chassisNumber;
    String engineNumber;
    Long ccOrKw;
    Long manufacturingYear;
    Long applicantId;
    Instant applicationDate;
    Long applicationStatusId;
    String applicationStatusName;

    // ******** For Service Request
    LocalDateTime forwardDateForInspection;
    Long inspectorId;
    Long inspectionStatusId;
    String inspectionRemarks;
    LocalDateTime inspectionDate;
    LocalDateTime forwardDateForRevenue;
    Long revenueCheckerId;
    Long revenueStatusId;
    String revenueRemarks;
    LocalDateTime revenueCheckDate;
    Long approvalId;
    String approvalRemarks;
    LocalDateTime approvalDate;
    LocalDateTime rejectionDate;

    String applicationStatusCode;

}
