package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class DLServiceRequestCreateRequest {

    @NotNull(message = "Service request number is required")
    @Size(max = 25, message = "Service request number cannot exceed 25 characters")
    private String serviceRequestNo;

    @NotNull(message = "DL info ID is required")
    private Long dlInfoId;

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

    private Long dlExamStatusId;

    private String dlExamRemarks;

    private LocalDateTime dlExamDate;

    private Long approvalId;

    private String approvalRemarks;

    private LocalDateTime approvalDate;

    private LocalDateTime rejectionDate;

    private Long applicationStatusId;

}
