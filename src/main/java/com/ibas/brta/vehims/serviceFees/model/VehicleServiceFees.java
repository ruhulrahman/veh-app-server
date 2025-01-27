package com.ibas.brta.vehims.serviceFees.model;

import java.time.LocalDateTime;
import java.util.List;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;
import com.ibas.brta.vehims.configurations.model.ServiceEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "f_vehicle_service_fees")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "v_service_fees_id"))

public class VehicleServiceFees extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "cc_min")
    private Integer ccMin;

    @Column(name = "cc_max")
    private Integer ccMax;

    @Column(name = "seat_min")
    private Integer seatMin;

    @Column(name = "seat_max")
    private Integer seatMax;

    @Column(name = "weight_min")
    private Integer weightMin;

    @Column(name = "weight_max")
    private Integer weightMax;

    @Column(name = "kw_min")
    private Integer kwMin;

    @Column(name = "kw_max")
    private Integer kwMax;

    @Column(name = "is_air_condition", nullable = true)
    private Boolean isAirCondition;

    @Column(name = "is_hire", nullable = true)
    private Boolean isHire;

    @Column(name = "is_yearly_fee", nullable = true)
    private Boolean isYearlyFee;

    @Column(name = "main_fee", nullable = false)
    private Integer mainFee = 0;

    @Column(name = "late_fee", nullable = true)
    private Integer lateFee = 0;

    @Column(name = "vat_percentage", nullable = true)
    private Short vatPercentage;

    @Column(name = "sd_percentage", nullable = true)
    private Short sdPercentage;

    @Column(name = "effective_start_date", nullable = false)
    private LocalDateTime effectiveStartDate;

    @Column(name = "effective_end_date")
    private LocalDateTime effectiveEndDate;

    @Column(name = "is_applicable_for_multiple_vehicle_owner", nullable = false)
    private Boolean isApplicableForMultipleVehicleOwner = false;

    @Column(name = "fee_for_multiple_vehicle", nullable = false)
    private Integer feeForMultipleVehicle = 0;

    @Column(name = "is_electric_vehicle", nullable = false)
    private Boolean isElectricVehicle = false;

}
