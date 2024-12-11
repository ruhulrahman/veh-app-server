package com.ibas.brta.vehims.configurations.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_vehicle_type_class_maps")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "type_class_map_id"))

public class VehicleTypeClassMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle Type cannot be null")
    @Column(name = "vehicle_type_id", nullable = false)
    private Long vehicleTypeId;

    @NotNull(message = "Vehicle Class cannot be null")
    @Column(name = "vehicle_class_id", nullable = false)
    private Long vehicleClassId;

}
