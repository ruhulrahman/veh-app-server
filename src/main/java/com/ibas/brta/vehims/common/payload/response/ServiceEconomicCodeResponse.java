package com.ibas.brta.vehims.common.payload.response;

import com.ibas.brta.vehims.configurations.payload.response.CountryResponse;
import com.ibas.brta.vehims.configurations.payload.response.LocationResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusResponse;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceEconomicCodeResponse {

    private Long id;
    private String orgCode;
    private String orgName;
    private String operationCode;
    private String operationName;
    private String fundCode;
    private String economicCode;
    private String economicDescriptionEn;
}
