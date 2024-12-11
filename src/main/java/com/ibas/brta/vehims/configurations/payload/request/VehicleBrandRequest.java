package com.ibas.brta.vehims.configurations.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleBrandRequest {
    private Long id;

    @NotNull(message = "Maker Id cannot be null")
    @Column(name = "maker_id", nullable = false)
    private Long makerId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
