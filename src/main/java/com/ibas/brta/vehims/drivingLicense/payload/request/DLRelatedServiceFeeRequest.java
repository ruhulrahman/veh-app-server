package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DLRelatedServiceFeeRequest {
    private Long id;

    private Long serviceId;

    private Boolean isYearlyFee = false;

    private Integer mainFee;

    private Integer lateFee;

    private Short vatPercentage;

    private Short sdPercentage;

    private LocalDateTime effectiveStartDate;

    private LocalDateTime effectiveEndDate;

    private Boolean isActive = false;

    private Long createUserId;

    private Long updateUserId;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

    private Integer versionNo;

}