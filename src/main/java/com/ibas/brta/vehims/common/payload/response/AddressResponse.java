package com.ibas.brta.vehims.common.payload.response;

import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;
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
    private Long divisionId;
    private String divisionNameEn;
    private String divisionNameBn;
    private Long districtId;
    private String districtNameEn;
    private String districtNameBn;
    private Long thanaId;
    private String postCode;
    private String holdingHouseVillage;
    private String roadBlockSectorColony;
    private String mobileNumber;
    private String fullAddressEn;
    private String fullAddressBn;
}
