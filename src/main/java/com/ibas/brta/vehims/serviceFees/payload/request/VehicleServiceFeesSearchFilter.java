package com.ibas.brta.vehims.serviceFees.payload.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleServiceFeesSearchFilter {
    private int page;
    private int size;
    private Long serviceId;
    private Long vehicleTypeId;
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
    private LocalDateTime effectiveStartDate;
    private LocalDateTime effectiveEndDate;
    private Boolean isActive;
}
