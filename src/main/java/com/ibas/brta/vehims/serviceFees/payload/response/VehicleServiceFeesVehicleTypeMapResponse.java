package com.ibas.brta.vehims.serviceFees.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleServiceFeesVehicleTypeMapResponse {
    private Long serviceFeesId;
    private Long vehicleClassId;
}
