package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_vehicle_types")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_type_id"))

public class VehicleType extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;
}