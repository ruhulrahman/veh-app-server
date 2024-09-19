package com.ibas.brta.vehims.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_statuses")

@Builder
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
    // @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Column(name = "status_group_id", nullable = false)
    private Long statusGroupId;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "status_group_id", insertable = false, updatable = false)
    // private StatusGroup statusGroup;

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

    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinTable(name = "c_status_groups", joinColumns = @JoinColumn(name =
    // "status_group_id"))
    // private StatusGroup statusGroup;

    // Many-to-One relationship with StatusGroup
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "status_group_id", referencedColumnName =
    // "status_group_id", insertable = false, updatable = false)
    // private StatusGroup statusGroup;

}
