package com.ibas.brta.vehims.model.configurations;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_vehicle_brands")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "brand_id"))

public class VehicleBrand extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Maker Id cannot be null")
    @Column(name = "maker_id", nullable = false)
    private Long makerId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;
}
