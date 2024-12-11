package com.ibas.brta.vehims.serviceFees.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DrivingRelatedServiceFeesResponse {
    private Long id;
    private Long serviceId;
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
}
