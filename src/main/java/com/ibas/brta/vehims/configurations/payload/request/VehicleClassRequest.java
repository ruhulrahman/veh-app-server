package com.ibas.brta.vehims.configurations.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleClassRequest {
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

    @NotBlank(message = "Symbol in English cannot be blank")
    @Size(max = 15)
    private String symbolEn;

    @NotBlank(message = "Symbol in Bangla cannot be blank")
    @Size(max = 15)
    private String symbolBn;

    private Integer startNumber;

    private Integer endNumber;

    @Size(max = 255)
    private String remarksEn;

    @Size(max = 255)
    private String remarksBn;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
