package com.ibas.brta.vehims.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleInfoResponse {
    private Long id;
    private String chassisNumber;
    private String engineNumber;
    private LocalDateTime fitnessIssueDate;
    private LocalDateTime fitnessExpireDate;
    private LocalDateTime taxTokenIssueDate;
    private LocalDateTime taxTokenExpireDate;
    private LocalDateTime routePermitIssueDate;
    private LocalDateTime routePermitExpireDate;
    private String billOfEntryNumber;
    private LocalDateTime billOfEntryDate;
    private String billOfEntryOfficeCode;
    private String hsCode;
    private Long importerId;
    private Long makerId;
    private Long makerCountryId;
    private Long exporterId;
    private String agent;
    private String productLocation;
    private String productDescription;
    private String invoiceNumber;
    private LocalDateTime invoiceDate;
    private Boolean isElectrictVehicle;
    private Integer ccOrKw;
    private Integer manufacturingYear;
    private Long vehicleTypeId;
    private Long vehicleClassId;
    private Long bodyColorId;
    private Long assemblyOperationId;
    private Integer mileage;
    private Integer unladenWeight;
    private Integer maxLadenWeight;
    private Boolean isHire;
    private Boolean isHirePurchase;
    private Integer totalSeat;
    private Long fuelId;
    private LocalDateTime economicLife;
    private LocalDateTime remainingLife;
    private LocalDateTime vehiclePrice;
    private Boolean isAirConditioner;
    private Long brandId;
    private String model;
    private Integer horsePower;
    private Integer highestRpm;
    private String wheelBase;
    private Integer standee;
    private String tyreSize;
    private Integer tyreNumber;
    private Integer axleNumber;
    private Integer frontAxle1;
    private Integer frontAxle2;
    private Integer centralAxle1;
    private Integer centralAxle2;
    private Integer centralAxle3;
    private Integer rearAxle1;
    private Integer rearAxle2;
    private Integer rearAxle3;
    private Integer statusId;
    private Integer pageCompleted;
}