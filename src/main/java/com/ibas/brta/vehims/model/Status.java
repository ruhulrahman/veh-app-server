package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_status")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "status_id"))

public class Status extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Status Group ID cannot be null")
    @Column(name = "status_group_id", nullable = false)
    private Integer statusGroupId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "status_code", nullable = false, unique = true)
    private String statusCode;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;

    @Size(max = 100)
    @Column(name = "color_name")
    private String colorName;

    @Min(value = 1, message = "Priority must be at least 1.")
    @Column(name = "priority")
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "c_status_groups", joinColumns = @JoinColumn(name = "status_group_id"), inverseJoinColumns = @JoinColumn(name = "status_id"))
    private StatusGroup statusGroup;

    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "statusGroupId", insertable = false, updatable = false)
    // private StatusGroup statusGroup;

}
