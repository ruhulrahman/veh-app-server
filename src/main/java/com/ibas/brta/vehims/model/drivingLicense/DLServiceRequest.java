package com.ibas.brta.vehims.model.drivingLicense;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.model.audit.DateAudit;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "dl_service_requests")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)

public class DLServiceRequest extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dl_service_request_id")
    private Long id;

    @Column(name = "service_request_no", nullable = false, length = 25)
    private String serviceRequestNo;

    @Column(name = "dl_info_id")
    private Long dlInfoId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "org_id", nullable = false)
    private Long orgId;

    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

    @Column(name = "application_date", nullable = false)
    private LocalDateTime applicationDate;

    @Column(name = "volume_no")
    private Integer volumeNo;

    @Column(name = "page_no")
    private Integer pageNo;

    @Column(name = "forward_date_for_inspection")
    private LocalDateTime forwardDateForInspection;

    @Column(name = "inspector_id")
    private Long inspectorId;

    @Column(name = "inspection_status_id")
    private Long inspectionStatusId;

    @Column(name = "inspection_remarks", length = 255)
    private String inspectionRemarks;

    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    @Column(name = "dl_exam_status_id")
    private Long dlExamStatusId;

    @Column(name = "dl_exam_remarks")
    private String dlExamRemarks;

    @Column(name = "dl_exam_date")
    private LocalDateTime dlExamDate;

    @Column(name = "approval_id")
    private Long approvalId;

    @Column(name = "approval_remarks")
    private String approvalRemarks;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column(name = "rejection_date")
    private LocalDateTime rejectionDate;

    @Column(name = "application_status_id")
    private Long applicationStatusId;

}
