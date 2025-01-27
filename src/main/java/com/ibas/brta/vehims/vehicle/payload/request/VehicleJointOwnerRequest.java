package com.ibas.brta.vehims.vehicle.payload.request;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.util.Date;

@Data
public class VehicleJointOwnerRequest {

    private Long id;

    @NotNull(message = "Vehicle Info ID is required")
    private Long vehicleInfoId;

    @NotNull(message = "Service Request ID is required")
    private Long serviceRequestId;

    @Column(name = "vehicle_owner_id")
    private Long vehicleOwnerId;

    @NotBlank(message = "Joint Owner Name is required")
    @Size(max = 100, message = "Joint Owner Name can be up to 100 characters long")
    private String jointOwnerName;

    private Long countryId;

    private Long addressTypeId;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    @NotBlank(message = "Post Code is required")
    @Size(max = 20, message = "Post Code can be up to 20 characters long")
    private String postCode;

    @Size(max = 100, message = "Holding House/Village can be up to 100 characters long")
    private String holdingHouseVillage;

    @Size(max = 100, message = "Road/Block/Sector/Colony can be up to 100 characters long")
    private String roadBlockSectorColony;

    @Size(max = 20, message = "Mobile Number can be up to 20 characters long")
    private String mobileNumber;

    private Boolean isActive;
}
