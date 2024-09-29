package com.ibas.brta.vehims.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_vehicle_routes")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "route_id"))

public class VehicleRoute extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Route Permit Type ID cannot be null")
    @Column(name = "route_permit_type_id", nullable = false)
    private Long routePermitTypeId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "route_name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "route_name_bn", nullable = false)
    private String nameBn;

    @NotNull(message = "Minimum District cannot be blank")
    @Column(name = "min_district", nullable = false)
    private Integer minDistrict;

    @NotNull(message = "Max District cannot be blank")
    @Column(name = "max_district", nullable = false)
    private Integer maxDistrict;
}
