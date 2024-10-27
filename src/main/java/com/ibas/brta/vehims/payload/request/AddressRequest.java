package com.ibas.brta.vehims.payload.request;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.Date;

@Data
public class AddressRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(max = 255, message = "Model can be up to 255 characters long")
    private String model;

    private Long modelId;
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

}
