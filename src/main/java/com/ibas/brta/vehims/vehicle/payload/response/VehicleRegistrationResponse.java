package com.ibas.brta.vehims.vehicle.payload.response;

import com.ibas.brta.vehims.configurations.payload.response.OrganizationResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleClassResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleRegistrationMarkResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleTypeResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class VehicleRegistrationResponse {
    private Long id;
    private Long vehicleInfoId;
    private Long serviceRequestId;
    private Long vehicleOwnerId;
    private Long regOfficeId;
    private Long vehicleTypeId;
    private Long vehicleClassId;
    private Long vehicleRegistrationMarkId;
    private String classNumber;
    private String vehicleNumber;
    private String fullRegNumber;
    private Long statusId;
    private Instant createdAt;

    private VehicleTypeResponse vehicleType;
    private VehicleClassResponse vehicleClass;
    private OrganizationResponse regOffice;
    private VehicleRegistrationMarkResponse vehicleRegistrationMark;
    private VehicleOwnerResponse vehicleOwner;
}
