package com.ibas.brta.vehims.payload.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DLServiceRequestResponse {
    private Long id;
    private String serviceRequestNo;
    private Long dlInfoId;
    private Long serviceId;
    private Long orgId;
    private Long applicantId;
    private LocalDateTime applicationDate;
    private Integer volumeNo;
    private Integer pageNo;
    private LocalDateTime forwardDateForInspection;
    private Long inspectorId;
    private Long inspectionStatusId;
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
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer versionNo;
}
