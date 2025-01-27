package com.ibas.brta.vehims.vehicle.payload.request;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.util.Date;

@Data
public class VehicleRegistrationRequest {

    private Long id;

    @NotNull(message = "Vehicle Info ID is required")
    private Long vehicleInfoId;

    @NotNull(message = "Service Request ID is required")
    private Long serviceRequestId;

    @NotNull(message = "Vehicle Owner ID is required")
    private Long vehicleOwnerId;

    @NotNull(message = "Registration Office ID is required")
    private Long regOfficeId;

    @NotNull(message = "Vehicle Type ID is required")
    private Long vehicleTypeId;

    @NotNull(message = "Vehicle Class ID is required")
    private Long vehicleClassId;

    private Long vehicleRegistrationMarkId;

    @NotNull(message = "Class Number is required")
    private String classNumber;

    @NotNull(message = "Vehicle Number is required")
    private String vehicleNumber;

    @NotNull(message = "Full Registration Number is required")
    private String fullRegNumber;

    @NotNull(message = "Status ID is required")
    private Long statusId;

}
