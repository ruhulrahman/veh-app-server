package com.ibas.brta.vehims.configurations.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRouteRequest {
    private Long id;

    @NotNull(message = "Route Permit Type ID cannot be null")
    @Column(name = "route_permit_type_id", nullable = false)
    private Long routePermitTypeId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "route_name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "route_name_bn", nullable = false)
    private String nameBn;

    @NotNull(message = "Minimum District cannot be blank")
    @Column(name = "min_district", nullable = false)
    private Integer minDistrict;

    @NotNull(message = "Max District cannot be blank")
    @Column(name = "max_district", nullable = false)
    private Integer maxDistrict;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
