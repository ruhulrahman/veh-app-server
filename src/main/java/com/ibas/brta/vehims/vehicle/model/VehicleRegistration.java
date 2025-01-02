package com.ibas.brta.vehims.vehicle.model;

import java.util.Date;

import com.ibas.brta.vehims.common.model.audit.DateAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "v_vehicle_registrations")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_reg_id"))

public class VehicleRegistration extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle Info ID is required")
    @Column(name = "vehicle_info_id", nullable = false)
    private Long vehicleInfoId;

    @NotNull(message = "Service Request ID is required")
    @Column(name = "v_service_request_id", nullable = false)
    private Long serviceRequestId;

    @NotNull(message = "Vehicle Owner ID is required")
    @Column(name = "vehicle_owner_id", nullable = false)
    private Long vehicleOwnerId;

    @NotNull(message = "Registration Office ID is required")
    @Column(name = "reg_office_id", nullable = false)
    private Long regOfficeId;

    @NotNull(message = "Vehicle Type ID is required")
    @Column(name = "vehicle_type_id", nullable = false)
    private Long vehicleTypeId;

    @NotNull(message = "Vehicle Class ID is required")
    @Column(name = "vehicle_class_id", nullable = false)
    private Long vehicleClassId;

    @NotNull(message = "Class Number is required")
    @Column(name = "class_number", nullable = false)
    private String classNumber;

    @NotNull(message = "Vehicle Number is required")
    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @NotNull(message = "Full Registration Number is required")
    @Column(name = "full_reg_number", nullable = false)
    private String fullRegNumber;

    @NotNull(message = "Status ID is required")
    @Column(name = "status_id", nullable = false)
    private Long statusId;

}
