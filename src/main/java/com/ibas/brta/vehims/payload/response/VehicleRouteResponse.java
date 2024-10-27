package com.ibas.brta.vehims.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRouteResponse {
    private Long id;
    private Long routePermitTypeId;
    private StatusResponse routePermitType;
    private String nameEn;
    private String nameBn;
    private Integer minDistrict;
    private Integer maxDistrict;
    private Boolean isActive;
}
