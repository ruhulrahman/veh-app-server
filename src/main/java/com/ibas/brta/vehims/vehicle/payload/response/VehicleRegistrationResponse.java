package com.ibas.brta.vehims.vehicle.payload.response;

import java.util.Date;

import lombok.Data;

@Data
public class VehicleRegistrationResponse {
    private Long id;
    private Long vehicleInfoId;
    private Long serviceRequestId;
    private Long vehicleOwnerId;
    private Long regOfficeId;
    private Long vehicleTypeId;
    private Long vehicleClassId;
    private String classNumber;
    private String vehicleNumber;
    private String fullRegNumber;
    private Long statusId;
}
