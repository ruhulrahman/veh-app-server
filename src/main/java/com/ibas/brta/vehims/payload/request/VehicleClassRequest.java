package com.ibas.brta.vehims.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleClassRequest {
    private Long id;

    @NotNull(message = "Vehicle Type cannot be null")
    @Column(name = "vehicle_type_id", nullable = false)
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
    @Column(name = "vehicle_class_code", nullable = false)
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

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
