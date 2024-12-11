package com.ibas.brta.vehims.serviceFees.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrivingRelatedServiceFeesRequest {
    private Long serviceId;
    private Boolean isYearlyFee;
    private Integer mainFee;
    private Integer lateFee;
    private Short vatPercentage;
    private Short sdPercentage;
    private LocalDateTime effectiveStartDate;
    private LocalDateTime effectiveEndDate;
    private Boolean isActive;
    private Long createUserId;
    private Long updateUserId;
    private Integer versionNo;
}
