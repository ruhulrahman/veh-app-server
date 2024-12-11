package com.ibas.brta.vehims.serviceFees.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ibas.brta.vehims.configurations.model.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VehicleServiceFeesResponse {
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
    private List<Long> vehicleTypeIds;
    private List<VehicleType> vehicleTypes;

}
