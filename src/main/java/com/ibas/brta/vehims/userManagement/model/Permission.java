package com.ibas.brta.vehims.userManagement.model;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;

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
@AttributeOverride(name = "id", column = @Column(name = "permission_id"))

public class Permission extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_permission_id", nullable = true)
    private Long parentId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;

    @NotBlank(message = "Address cannot be blank")
    @Column(name = "permission_code", nullable = false)
    private String permissionCode;

    @NotNull(message = "Type cannot be null")
    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "page_url")
    private String pageUrl;

    // @ManyToMany(mappedBy = "permissions")
    // private Collection<Role> roles;

}
