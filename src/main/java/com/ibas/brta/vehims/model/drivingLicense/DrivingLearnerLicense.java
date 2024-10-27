package com.ibas.brta.vehims.model.drivingLicense;

import com.ibas.brta.vehims.model.audit.DateAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "s_driving_learner_licenses")
public class DrivingLearnerLicense extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dll_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dl_service_request_id", nullable = false)
    private Long dlServiceRequestId;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "applicant_id", nullable = false)
//    private SUser applicant;

    @NotNull
    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "photo_media_id")
//    private Media photoMedia;

    @Column(name = "photo_media_id")
    private Long photoMediaId;

    @NotNull
    @Column(name = "learner_number", nullable = false)
    private Long learnerNumber;

    @NotNull
    @Column(name = "learner_applicant_id", nullable = false)
    private Long learnerApplicantId;

    @NotNull
    @Column(name = "roll_no", nullable = false)
    private Integer rollNo;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "office_id", nullable = false)
//    private Office office;
    @NotNull
    @Column(name = "office_id", nullable = false)
    private Long officeId;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "exam_venue_id", nullable = false)
//    private ExamCenter examVenue;

    @NotNull
    @Column(name = "exam_venue_id", nullable = false)
    private Long examVenueId;

    @NotNull
    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @NotNull
    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "dl_language_id", nullable = false)
//    private Status dlLanguage;

    @NotNull
    @Column(name = "dl_language_id", nullable = false)
    private Long dlLanguageId;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "application_type_id", nullable = false)
//    private Status applicationType;

    @NotNull
    @Column(name = "application_type_id", nullable = false)
    private Long applicationTypeId;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "license_type_id", nullable = false)
//    private Status licenseType;

    @NotNull
    @Column(name = "license_type_id", nullable = false)
    private Long licenseTypeId;

    @NotNull
    @Column(name = "exam_date", nullable = false)
    private LocalDateTime examDate;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "exam_status_id", nullable = false)
//    private Status examStatus;

    @NotNull
    @Column(name = "exam_status_id", nullable = false)
    private Long examStatus;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}