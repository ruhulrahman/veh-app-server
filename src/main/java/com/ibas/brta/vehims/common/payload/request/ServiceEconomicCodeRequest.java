package com.ibas.brta.vehims.common.payload.request;

import lombok.Data;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

@Data
public class ServiceEconomicCodeRequest {
    private Long id;
    private String orgCode;
    private String orgName;
    private String operationCode;
    private String operationName;
    private String fundCode;
    private String economicCode;
    private String economicDescriptionEn;
}
