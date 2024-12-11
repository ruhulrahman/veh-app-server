package com.ibas.brta.vehims.serviceFees.model;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;
import com.ibas.brta.vehims.configurations.model.ServiceEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "f_driving_related_service_fees")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "dr_service_fees_id"))

public class DrivingRelatedServiceFees extends RecordAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "is_yearly_fee", nullable = false)
    private Boolean isYearlyFee = true;

    @Column(name = "main_fee", nullable = false)
    private Integer mainFee;

    @Column(name = "late_fee", nullable = true, columnDefinition = "INTEGER DEFAULT 0")
    private Integer lateFee = 0;

    @Column(name = "vat_percentage")
    private Short vatPercentage;

    @Column(name = "sd_percentage")
    private Short sdPercentage;

    @Column(name = "effective_start_date", nullable = false)
    private LocalDateTime effectiveStartDate;

    @Column(name = "effective_end_date")
    private LocalDateTime effectiveEndDate;

    // @ManyToOne
    // @JoinColumn(name = "service_id", referencedColumnName = "service_id",
    // insertable = false, updatable = false)
    // private ServiceEntity service;
}
