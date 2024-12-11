package com.ibas.brta.vehims.configurations.model;

import java.util.List;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_services")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "service_id"))

public class ServiceEntity extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;

    @NotBlank
    @Size(max = 100)
    @Column(name = "service_code", nullable = false, unique = true)
    private String serviceCode;

    @Column(name = "parent_service_id", nullable = true)
    private Long parentServiceId;

    @Column(name = "child_service_ids", nullable = true)
    private List<Long> childServiceIds;

    @Min(value = 1, message = "Priority must be at least 1.")
    @Column(name = "priority")
    private Integer priority;

    @Column(name = "service_economic_code_id")
    private Long serviceEconomicCodeId;

    // // Self-referencing relationship
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "parent_service_id")
    // private ServiceEntity parentService;

    // @OneToMany(mappedBy = "parentService", cascade = CascadeType.ALL,
    // orphanRemoval = true)
    // private List<ServiceEntity> childServices;
}
