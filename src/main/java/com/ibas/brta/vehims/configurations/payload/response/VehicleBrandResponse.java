package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleBrandResponse {
    private Long id;
    private Long makerId;
    private VehicleMakerResponse maker;
    private String nameEn;
    private String nameBn;
    private Boolean isActive;
}
