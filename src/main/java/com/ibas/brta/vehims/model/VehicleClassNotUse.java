package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_vehicle_classes_not_use")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_class_id"))

public class VehicleClassNotUse extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle Type cannot be null")
    @Column(name = "vehicle_type_id", nullable = true)
    private Long vehicleTypeId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;

    @NotBlank(message = "Vehicle Class Code cannot be blank")
    @Column(name = "vehicle_class_code", nullable = true)
    private String vehicleClassCode;

    @Column(name = "cc_min")
    private Integer ccMin;

    @Column(name = "cc_max")
    private Integer ccMax;

    @Column(name = "seat_min")
    private Integer seatMin;

    @Column(name = "seat_max")
    private Integer seatMax;

    @Column(name = "loaded_weight_min_kg")
    private Integer loadedWeightMinKg;

    @Column(name = "loaded_weight_max_kg")
    private Integer loadedWeightMaxKg;

    @Column(name = "motor_capacity_min_kw")
    private Integer motorCapacityMinKw;

    @Column(name = "motor_capacity_max_kw")
    private Integer motorCapacityMaxKw;
}
