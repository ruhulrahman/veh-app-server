package com.ibas.brta.vehims.model.rbac;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibas.brta.vehims.model.audit.RecordAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "x_menus")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdDate", "updatedDate", "modifiedBy", "createdBy"})
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name="id", column=@Column(name="menu_id"))
@EqualsAndHashCode(callSuper=false)

public class Menu extends RecordAudit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name_en", nullable=false)
    private String menuNameEn;

    @Column(name = "menu_name_bn")
    private String menuNameBn;

    @Column(name = "menu_code")
    private String menuCode;

    @Column(name = "menu_url", nullable = true)
    private String menuUrl;

    @Column(name = "parent_menu_id")
    private Long parentMenuId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "x_role_menus", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Role> roles = new HashSet<>();
}