package com.ibas.brta.vehims.vehicle.payload.response;

import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.MediaResult;
import com.ibas.brta.vehims.configurations.payload.response.OrganizationResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleFitnessResponse {
    private Long id;
    private Long serviceRequestId;
    private Long vehicleInfoId;
    private LocalDateTime fitnessValidStartDate;
    private LocalDateTime fitnessValidEndDate;
    private Boolean isActive;
}
