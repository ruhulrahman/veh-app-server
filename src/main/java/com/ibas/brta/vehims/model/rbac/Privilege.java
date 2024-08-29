package com.ibas.brta.vehims.model.rbac;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibas.brta.vehims.model.audit.DateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "x_privileges")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdDate", "updatedDate"})
@EqualsAndHashCode(callSuper=false)
public class Privilege extends DateAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}