package com.ibas.brta.vehims.vehicle.payload.response;

import com.ibas.brta.vehims.configurations.payload.response.FuelTypeResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleClassResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleColorResponse;
import com.ibas.brta.vehims.configurations.payload.response.VehicleTypeResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleInfoFullResponse {
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
    private LocalDate billOfEntryDate;
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
    private LocalDate invoiceDate;
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
    private LocalDate economicLife;
    private LocalDate remainingLife;
    private Integer vehiclePrice;
    private Boolean isAirConditioner;
    private Long brandId;
    private String model;
    private Integer cylinder;
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
    private Integer overallLength;
    private Integer overallWidth;
    private Integer overallHeight;
    private Integer overhangsFront;
    private Integer overhangsRear;
    private Integer overhangsOther;
    private Integer statusId;
    private Integer pageCompleted;

    private VehicleClassResponse vehicleClass;
    private VehicleTypeResponse vehicleType;
    private VehicleColorResponse vehicleColor;
    private FuelTypeResponse fuelType;

    private UserNidInfoResponse userNidInfo;
    private VehicleOwnerResponse vehicleOwner;

}
