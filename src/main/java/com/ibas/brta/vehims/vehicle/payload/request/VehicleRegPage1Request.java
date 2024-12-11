package com.ibas.brta.vehims.vehicle.payload.request;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRegPage1Request {
    private Long id;

    @Size(max = 100)
    private String billOfEntryNumber;

    private LocalDate billOfEntryDate;

    @Size(max = 100)
    private String billOfEntryOfficeCode;

    @Size(max = 100)
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

    @NotBlank(message = "Chassis Number cannot be blank")
    @Size(max = 100)
    private String chassisNumber;

    @NotBlank(message = "Engine Number cannot be blank")
    @Size(max = 100)
    private String engineNumber;

    private Integer pageCompleted;
    private Long serviceRequestId;
    private Long vehicleInfoId;
}
