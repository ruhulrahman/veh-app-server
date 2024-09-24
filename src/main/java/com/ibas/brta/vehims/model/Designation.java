package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.RecordAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "c_designations")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = false)
@AttributeOverride(name = "id", column = @Column(name = "designation_id"))
public class Designation extends RecordAudit {
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

    @NotNull(message = "The level number not be null.")
    @Min(value = 1, message = "level number must be at least 1.")
    @Max(value = 1000, message = "level number must be at most 1000.")
    private Integer levelNumber;

    private Long parentDesignationId;

    // Self-referencing relationship: parentDesignationId refers to id of parent
    // Designation

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentDesignationId", insertable = false, updatable = false)
    private Designation parentDesignation;

    // @OneToMany(mappedBy = "parentDesignation", cascade = CascadeType.ALL)
    // @OneToOne
    // @PrimaryKeyJoinColumn(name = "parentDesignationId")
    // private Designation parentDesignation;

    // @OneToMany(mappedBy = "parentDesignation", cascade = CascadeType.ALL)
    // private List<Designation> subDesignations; // To retrieve sub-designations
}
