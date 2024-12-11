package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleClassResponse {
    private Long id;
    private Long vehicleTypeId;
    private VehicleTypeResponse vehicleType;
    private String nameEn;
    private String nameBn;
    private String vehicleClassCode;
    private String symbolEn;
    private String symbolBn;
    private Integer startNumber;
    private Integer endNumber;
    private String remarksEn;
    private String remarksBn;
    private Boolean isActive;
}
