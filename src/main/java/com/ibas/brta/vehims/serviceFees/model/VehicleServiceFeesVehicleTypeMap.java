package com.ibas.brta.vehims.serviceFees.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "f_vehicle_service_fees_vehicle_type_maps")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleServiceFeesVehicleTypeMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle Service Fees Id cannot be null")
    @Column(name = "v_service_fees_id", nullable = false)
    private Long serviceFeesId;

    @NotNull(message = "Vehicle Type cannot be null")
    @Column(name = "vehicle_type_id", nullable = false)
    private Long vehicleTypeId;

}
