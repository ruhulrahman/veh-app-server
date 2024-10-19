package com.ibas.brta.vehims.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.ibas.brta.vehims.model.audit.DateAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "u_permissions")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_info_id"))

public class VehicleInfo extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Chassis Number cannot be blank")
    @Size(max = 100)
    @Column(name = "chassis_number", nullable = false)
    private String chassisNumber;

    @NotBlank(message = "Engine Number cannot be blank")
    @Size(max = 100)
    @Column(name = "engine_number", nullable = false)
    private String engineNumber;

    @Column(name = "fitness_issue_date")
    private LocalDateTime fitnessIssueDate;

    @Column(name = "fitness_expire_date")
    private LocalDateTime fitnessExpireDate;

    @Column(name = "tax_token_issue_date")
    private LocalDateTime taxTokenIssueDate;

    @Column(name = "tax_token_expire_date")
    private LocalDateTime taxTokenExpireDate;

    @Column(name = "route_permit_issue_date")
    private LocalDateTime routePermitIssueDate;

    @Column(name = "route_permit_expire_date")
    private LocalDateTime routePermitExpireDate;

    @Size(max = 100)
    @Column(name = "bill_of_entry_number", nullable = true)
    private String billOfEntryNumber;

    @Column(name = "bill_of_entry_date")
    private LocalDateTime billOfEntryDate;

    @Size(max = 100)
    @Column(name = "bill_of_entry_office_code", nullable = true)
    private String billOfEntryOfficeCode;

    @Size(max = 100)
    @Column(name = "hs_code", nullable = true)
    private String hsCode;

    @Column(name = "importer_id", nullable = true)
    private Long importerId;

    @Column(name = "maker_id", nullable = true)
    private Long makerId;

    @Column(name = "maker_country_id", nullable = true)
    private Long makerCountryId;

    @Column(name = "exporter_id", nullable = true)
    private Long exporterId;

    @Column(name = "agent", nullable = true)
    private String agent;

    @Column(name = "product_location", nullable = true)
    private String productLocation;

    @Column(name = "product_description", nullable = true)
    private String productDescription;

    @Column(name = "invoice_number", nullable = true)
    private String invoiceNumber;

    @Column(name = "invoice_date", nullable = true)
    private LocalDateTime invoiceDate;

    @Column(name = "is_electrict_vehicle")
    private Boolean isElectrictVehicle;

    @Column(name = "cc_or_kw")
    private Integer ccOrKw;

    @Column(name = "manufacturing_year")
    private Integer manufacturingYear;

    @Column(name = "vehicle_type_id")
    private Long vehicleTypeId;

    @Column(name = "vehicle_class_id")
    private Long vehicleClassId;

    @Column(name = "body_color_id")
    private Long bodyColorId;

    @Column(name = "assembly_operation_id")
    private Long assemblyOperationId;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "unladen_weight")
    private Integer unladenWeight;

    @Column(name = "max_laden_weight")
    private Integer maxLadenWeight;

    @Column(name = "is_hire")
    private Boolean isHire;

    @Column(name = "is_hire_purchase")
    private Boolean isHirePurchase;

    @Column(name = "total_seat")
    private Integer totalSeat;

    @Column(name = "fuel_id")
    private Long fuelId;

    @Column(name = "economic_life")
    private LocalDateTime economicLife;

    @Column(name = "remaining_life")
    private LocalDateTime remainingLife;

    @Column(name = "vehicle_price")
    private LocalDateTime vehiclePrice;

    @Column(name = "is_air_conditioner")
    private Boolean isAirConditioner;

    @Column(name = "v_brand_id")
    private Long brandId;

    @Size(max = 100)
    @Column(name = "v_model")
    private String model;

    @Column(name = "horse_power")
    private Integer horsePower;

    @Column(name = "highest_rpm")
    private Integer highestRpm;

    @Size(max = 100)
    @Column(name = "wheel_base")
    private String wheelBase;

    @Column(name = "standee")
    private Integer standee;

    @Size(max = 100)
    @Column(name = "tyre_size")
    private String tyreSize;

    @Column(name = "tyre_number")
    private Integer tyreNumber;

    @Column(name = "axle_number")
    private Integer axleNumber;

    @Column(name = "front_axle_1")
    private Integer frontAxle1;

    @Column(name = "front_axle_2")
    private Integer frontAxle2;

    @Column(name = "central_axle_1")
    private Integer centralAxle1;

    @Column(name = "central_axle_2")
    private Integer centralAxle2;

    @Column(name = "central_axle_3")
    private Integer centralAxle3;

    @Column(name = "rear_axle_1")
    private Integer rearAxle1;

    @Column(name = "rear_axle_2")
    private Integer rearAxle2;

    @Column(name = "rear_axle_3")
    private Integer rearAxle3;

    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "page_completed")
    private Integer pageCompleted;

}
