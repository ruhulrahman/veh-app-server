package com.ibas.brta.vehims.vehicle.model;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "v_vehicle_tax_tokens")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_tt_id"))

public class VehicleTaxToken extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "v_service_request_id")
    private Long serviceRequestId;

    @Column(name = "vehicle_info_id")
    private Long vehicleInfoId;

    @Column(name = "tt_valid_start_date")
    private LocalDateTime ttValidStartDate;

    @Column(name = "tt_valid_end_date")
    private LocalDateTime ttValidEndDate;

}
