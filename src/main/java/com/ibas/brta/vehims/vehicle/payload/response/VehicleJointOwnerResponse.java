package com.ibas.brta.vehims.vehicle.payload.response;

import java.util.Date;

import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehicleJointOwnerResponse {
    private Long id;

    private Long vehicleInfoId;

    private Long serviceRequestId;

    private Long vehicleOwnerId;

    private String jointOwnerName;

    private Long countryId;

    private Long addressTypeId;

    private Long locationId;

    private String postCode;

    private String holdingHouseVillage;

    private String roadBlockSectorColony;

    private String mobileNumber;

    private Boolean isActive;

    private CountryResponse country;

    private StatusResponse addressType;

    private LocationResponse location;

    private Long divisionId;

    private String divisionNameEn;

    private String divisionNameBn;

    private Long districtId;

    private String districtNameEn;

    private String districtNameBn;

    private Long thanaId;

    private String fullAddressEn;

    private String fullAddressBn;
}
