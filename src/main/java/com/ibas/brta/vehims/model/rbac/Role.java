package com.ibas.brta.vehims.model.rbac;

import com.ibas.brta.vehims.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "x_roles")
@Data
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "name_en", length = 60)
    private RoleName name;

    @Column(name = "name_bn")
    private String altName;

    @Column(name = "role_code", nullable = false)
    private String code;

    @Column(nullable = false)
    private Boolean isActive = true;

    @ManyToMany
    @JoinTable(name = "x_role_privileges", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Collection<Privilege> privileges;
}
