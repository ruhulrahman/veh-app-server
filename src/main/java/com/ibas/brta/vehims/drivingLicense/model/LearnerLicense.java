package com.ibas.brta.vehims.drivingLicense.model;

import com.ibas.brta.vehims.common.model.audit.DateAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "s_driving_learner_licenses")
public class LearnerLicense extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dll_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dl_service_request_id", nullable = false)
    private Long dlServiceRequestId;

    // @NotNull
    @Column(name = "applicant_id", nullable = true)
    private Long applicantId;

    @Column(name = "photo_media_id")
    private Long photoMediaId;

    // @NotNull
    @Column(name = "learner_number", nullable = true)
    private String learnerNumber;

    @Column(name = "learner_applicant_id")
    private String learnerApplicantId;

    // @NotNull
    @Column(name = "roll_no", nullable = true)
    private Integer rollNo;

    // @NotNull
    @Column(name = "office_id", nullable = true)
    private Long officeId;

    // @NotNull
    @Column(name = "exam_venue_id", nullable = true)
    private Long examVenueId;

    // @NotNull
    @Column(name = "issue_date", nullable = true)
    private LocalDateTime issueDate;

    // @NotNull
    @Column(name = "expire_date", nullable = true)
    private LocalDateTime expireDate;

    // @NotNull
    @Column(name = "dl_language_id", nullable = true)
    private Long dlLanguageId;

    // @NotNull
    @Column(name = "application_type_id", nullable = true)
    private Long applicationTypeId;

    // @NotNull
    @Column(name = "license_type_id", nullable = true)
    private Long licenseTypeId;

    // @NotNull
    @Column(name = "exam_date", nullable = true)
    private LocalDateTime examDate;

    @Column(name = "exam_status_id")
    private Long examStatusId;

    @Column(name = "exam_attended_date")
    private LocalDateTime examAttendedDate;

    @Column(name = "is_paid")
    private Boolean isPaid = false;

}