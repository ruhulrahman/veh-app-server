package com.ibas.brta.vehims.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressResponse {

    private Long id;
    private Long userId;
    private String model;
    private Long modelId;
    private Long countryId;
    private CountryResponse country;
    private Long addressTypeId;
    private StatusResponse addressType;
    private Long locationId;
    private LocationResponse location;
    private String postCode;
    private String holdingHouseVillage;
    private String roadBlockSectorColony;
    private String mobileNumber;
}
