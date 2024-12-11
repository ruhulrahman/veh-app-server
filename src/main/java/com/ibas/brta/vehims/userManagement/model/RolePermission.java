package com.ibas.brta.vehims.userManagement.model;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "u_role_permissions")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "role_permission_id"))

public class RolePermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Role Id cannot be null")
    @Column(name = "role_id", nullable = false, unique = true)
    private Long roleId;

    @NotNull(message = "Permission Id cannot be null")
    @Column(name = "permission_id", nullable = false, unique = true)
    private Long permissionId;

}
