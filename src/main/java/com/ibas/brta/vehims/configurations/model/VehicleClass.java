package com.ibas.brta.vehims.configurations.model;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_vehicle_classes")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_class_id"))

public class VehicleClass extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle Type cannot be null")
    @Column(name = "vehicle_type_id", nullable = true)
    private Long vehicleTypeId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;

    @NotBlank(message = "Vehicle Class Code cannot be blank")
    @Column(name = "vehicle_class_code", nullable = true)
    private String vehicleClassCode;

    @NotBlank(message = "Symbol in English cannot be blank")
    @Size(max = 15)
    @Column(name = "symbol_en", nullable = false)
    private String symbolEn;

    @NotBlank(message = "Symbol in Bangla cannot be blank")
    @Size(max = 15)
    @Column(name = "symbol_bn", nullable = false)
    private String symbolBn;

    @Column(name = "start_number")
    private Integer startNumber;

    @Column(name = "end_number")
    private Integer endNumber;

    @Size(max = 255)
    @Column(name = "remarks_en", nullable = true)
    private String remarksEn;

    @Size(max = 255)
    @Column(name = "remarks_bn", nullable = true)
    private String remarksBn;

}
