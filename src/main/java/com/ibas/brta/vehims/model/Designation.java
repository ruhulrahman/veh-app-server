package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.RecordAudit;
import jakarta.persistence.*;
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
public class Designation extends RecordAudit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;
    @Column(name = "name_bn")
    private String nameBn;
    private int levelNumber;
    @Column(name = "parent_desingation_id")
    private Long parentDesignationId;

    // Self-referencing relationship: parentDesignationId refers to id of parent
    // Designation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentDesignationId")
    private Designation parentDesignation;

    @OneToMany(mappedBy = "parentDesignation", cascade = CascadeType.ALL)
    private List<Designation> subDesignations; // To retrieve sub-designations
}
