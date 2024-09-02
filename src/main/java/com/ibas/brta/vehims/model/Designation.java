package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.RecordAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    private String nameInEng;
    @Column(name = "name_bn")
    private String nameInBangla;
    private int levelNumber;
    private Long parentDesingationId;
}
