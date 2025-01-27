package com.ibas.brta.vehims.serviceFees.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "f_vehicle_service_fees_rules")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleServiceFeesRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "v_service_fees_rule_id")
    private Long id;

    @NotNull(message = "Service Id cannot be null")
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotNull(message = "Vehicle Type cannot be null")
    @Column(name = "vehicle_type_id", nullable = false)
    private Long vehicleTypeId;

    @NotNull(message = "Vehicle Service Fees Id cannot be null")
    @Column(name = "v_service_fees_id")
    private Long serviceFeesId;

//    @NotNull(message = "Status cannot be null")
//    @Column(name = "status_id", nullable = false)
//    private Long statusId;

    @Column(name = "status_ids", nullable = true)
    private List<Long> statusIds;


}
