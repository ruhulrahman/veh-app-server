package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleMakerResponse {
    private Long id;
    private Long countryId;
    private CountryResponse country;
    private String address;
    private String nameEn;
    private String nameBn;
    private Boolean isActive;
}
