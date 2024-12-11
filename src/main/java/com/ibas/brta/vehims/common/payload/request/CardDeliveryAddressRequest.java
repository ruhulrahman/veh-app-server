package com.ibas.brta.vehims.common.payload.request;

import lombok.Data;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

@Data
public class CardDeliveryAddressRequest {
    private Long id;
    private String model;
    private Long modelId;
    private Long addressTypeId;

    private Long divisionId;
    private Long districtId;
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

    private Long cardStatusId;
    private Long deliveryStatusId;
    private Long deliveredUserId;
    private LocalDateTime deliveredDate;
}
