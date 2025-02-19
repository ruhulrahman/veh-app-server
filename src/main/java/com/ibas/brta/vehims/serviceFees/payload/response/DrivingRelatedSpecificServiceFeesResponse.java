package com.ibas.brta.vehims.serviceFees.payload.response;

import com.ibas.brta.vehims.common.payload.response.ServiceEconomicCodeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DrivingRelatedSpecificServiceFeesResponse {
    private Long id;
    private Long serviceId;
    private String serviceCode;
    private String serviceNameEn;
    private String serviceNameBn;
    private Boolean isYearlyFee;
    private Integer mainFee;
    private Integer lateFee;
    private Short vatPercentage;
    private Short sdPercentage;
    private LocalDateTime effectiveStartDate;
    private LocalDateTime effectiveEndDate;
    private Boolean isActive;

    private Integer serviceFee;
    ServiceEconomicCodeResponse serviceEconomicCode;
}
