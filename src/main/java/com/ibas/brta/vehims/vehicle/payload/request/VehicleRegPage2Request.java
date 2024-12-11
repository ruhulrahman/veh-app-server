package com.ibas.brta.vehims.vehicle.payload.request;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRegPage2Request {
    private Long id;

    @NotNull(message = "Vehicle Type cannot be null")
    private Long vehicleTypeId;

    @NotNull(message = "Vehicle Class cannot be null")
    private Long vehicleClassId;

    @NotNull(message = "Is Electric Vehicle cannot be null")
    private Boolean isElectrictVehicle;

    @NotNull(message = "CC or KW cannot be null")
    private Integer ccOrKw;

    @NotBlank(message = "Chassis Number cannot be null")
    private String chassisNumber;

    @NotBlank(message = "Engine Number cannot be null")
    private String engineNumber;

    @NotNull(message = "Manufacturing Year cannot be null")
    private Integer manufacturingYear;

    @NotNull(message = "Body Color cannot be null")
    private Long bodyColorId;

    @NotNull(message = "Assembly Operation cannot be null")
    private Long assemblyOperationId;

    @NotNull(message = "Mileage cannot be null")
    private Integer mileage;

    @NotNull(message = "Unladen Weight cannot be null")
    private Integer unladenWeight;

    @NotNull(message = "Max Laden Weight cannot be null")
    private Integer maxLadenWeight;

    @NotNull(message = "Is Hire cannot be null")
    private Boolean isHire;

    @NotNull(message = "Is Hire Purchase cannot be null")
    private Boolean isHirePurchase;

    @NotNull(message = "Total Seat cannot be null")
    private Integer totalSeat;

    @NotNull(message = "Fuel cannot be null")
    private Long fuelId;

    private Long brandId;

    private Boolean isAirConditioner;

    @NotNull(message = "Vehicle Price cannot be null")
    private Integer vehiclePrice;

    private LocalDate economicLife;

    private LocalDate remainingLife;

    @Size(max = 100)
    private String model;

    private Integer cylinder;

    private Integer horsePower;

    private Integer highestRpm;

    @Size(max = 100)
    private String wheelBase;

    private Integer standee;

    // Vehicle Details Info======================================

    @Size(max = 100)
    @NotBlank(message = "Tyre Size cannot be null")
    private String tyreSize;

    @NotNull(message = "Tyre Number cannot be null")
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
    private Integer pageCompleted;

    private Long serviceRequestId;
    private Long vehicleInfoId;

}
