package com.ibas.brta.vehims.serviceFees.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleServiceFeesRequest {
    private Long id;
    private Long serviceId;
    private Integer ccMin;
    private Integer ccMax;
    private Integer seatMin;
    private Integer seatMax;
    private Integer weightMin;
    private Integer weightMax;
    private Integer kwMin;
    private Integer kwMax;
    private Boolean isAirCondition;
    private Boolean isHire;
    private Boolean isYearlyFee;
    private Integer mainFee;
    private Integer lateFee;
    private Short vatPercentage;
    private Short sdPercentage;
    private LocalDateTime effectiveStartDate;
    private LocalDateTime effectiveEndDate;
    private Boolean isActive;

    @NotNull
    private List<Long> vehicleTypeIds;
}
