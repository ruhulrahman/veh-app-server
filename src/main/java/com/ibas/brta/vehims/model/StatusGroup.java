package com.ibas.brta.vehims.model;

import java.util.HashSet;
import java.util.Set;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_status_groups")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "status_group_id"))

public class StatusGroup extends RecordAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "status_group_code", nullable = false, unique = true)
    private String statusGroupCode;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;

    @OneToMany(mappedBy = "statusGroup", cascade = CascadeType.ALL)
    private Set<Status> statuses = new HashSet<>();

}