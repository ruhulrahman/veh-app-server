package com.ibas.brta.vehims.vehicle.payload.request;

import lombok.Data;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class VServiceRequestCreateRequest {

    @NotNull(message = "Service request number is required")
    @Size(max = 25, message = "Service request number cannot exceed 25 characters")
    private String serviceRequestNo;

    @NotNull(message = "Vehicle info ID is required")
    private Long vehicleInfoId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    private Long orgId;

    @NotNull(message = "Applicant ID is required")
    private Long applicantId;

    @PastOrPresent(message = "Application date cannot be in the future")
    private LocalDateTime applicationDate;

    @Min(value = 1, message = "Volume number must be at least 1")
    private Integer volumeNo;

    @Min(value = 1, message = "Page number must be at least 1")
    private Integer pageNo;

    @FutureOrPresent(message = "Forward date for inspection cannot be in the past")
    private LocalDateTime forwardDateForInspection;

    private Long inspectorId;

    private Long inspectionStatusId;

    @Size(max = 255, message = "Inspection remarks cannot exceed 255 characters")
    private String inspectionRemarks;

    private LocalDateTime inspectionDate;

    @FutureOrPresent(message = "Forward date for revenue cannot be in the past")
    private LocalDateTime forwardDateForRevenue;

    private Long revenueCheckerId;

    private Long revenueStatusId;

    private String revenueRemarks;

    private LocalDateTime revenueCheckDate;

    private Long approvalId;

    private String approvalRemarks;

    private LocalDateTime approvalDate;

    private LocalDateTime rejectionDate;

    private Long applicationStatusId;

}