package com.ibas.brta.vehims.model.drivingLicense;

import com.ibas.brta.vehims.model.audit.RecordAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "f_driving_related_service_fees")
public class DrivingLicenseRelatedServiceFee extends RecordAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dr_service_fees_id", nullable = false)
    private Long id;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "service_id", nullable = false)
//    private ServiceEntity service;

    @NotNull
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_yearly_fee", nullable = false)
    private Boolean isYearlyFee = false;

    @NotNull
    @Column(name = "main_fee", nullable = false)
    private Integer mainFee;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "late_fee", nullable = false)
    private Integer lateFee;

    @NotNull
    @Column(name = "vat_percentage", nullable = false)
    private Short vatPercentage;

    @Column(name = "sd_percentage")
    private Short sdPercentage;

    @NotNull
    @Column(name = "effective_start_date", nullable = false)
    private LocalDateTime effectiveStartDate;

    @Column(name = "effective_end_date")
    private LocalDateTime effectiveEndDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}