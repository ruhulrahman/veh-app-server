package com.ibas.brta.vehims.vehicle.model;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "v_vehicle_advance_income_taxes")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_ait_id"))

public class VehicleAdvanceIncomeTax extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "v_service_request_id")
    private Long serviceRequestId;

    @Column(name = "vehicle_info_id")
    private Long vehicleInfoId;

    @Column(name = "fiscal_year_id")
    private Long fiscalYearId;

    @Column(name = "payment_status_id")
    private Long paymentStatusId;

}
