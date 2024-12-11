package com.ibas.brta.vehims.common.payload.response;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDeliveryAddressResponse {

    private Long id;
    private String model;
    private Long modelId;
    private Long addressTypeId;
    private StatusResponse addressType;
    private String holdingHouseVillage;
    private String roadBlockSectorColony;
    private Long locationId;
    private Long divisionId;
    private Long districtId;
    private Long thanaId;
    private LocationResponse location;
    private String postCode;
    private String mobileNumber;
    private Long cardStatusId;
    private Long deliveryStatusId;
    private Long deliveredUserId;
    private LocalDateTime deliveredDate;
}
