package com.ibas.brta.vehims.vehicle.payload.response;

import java.util.Date;

import lombok.Data;

@Data
public class VehRegNumberGenerationResponse {
    private String classNumber;
    private String vehicleNumber;
    private String fullRegNumber;
}
