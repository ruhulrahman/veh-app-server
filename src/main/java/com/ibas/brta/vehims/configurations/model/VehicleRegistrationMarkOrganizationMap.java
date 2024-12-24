package com.ibas.brta.vehims.configurations.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_vehicle_registration_mark_organization_maps")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "v_reg_mark_org_map_id"))

public class VehicleRegistrationMarkOrganizationMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Organization cannot be null")
    @Column(name = "org_id", nullable = false)
    private Long orgId;

    @NotNull(message = "Vehicle Registration Mark cannot be null")
    @Column(name = "vechile_registration_mark_id", nullable = false)
    private Long vehicleRegistrationMarkId;

}
