package com.ibas.brta.vehims.configurations.model;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "c_office_exam_centers")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "office_exam_center_id"))

public class ExamCenter extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Organization Id cannot be null")
    @Column(name = "org_id", nullable = false)
    private Long orgId;

    @NotNull(message = "Thana Id cannot be null")
    @Column(name = "thana_id", nullable = false)
    private Long thanaId;

    @NotNull(message = "Address in English cannot be null")
    @Size(max = 100)
    @Column(name = "address_en", nullable = false)
    private String addressEn;

    @NotNull(message = "Address in Bangla cannot be null")
    @Size(max = 100)
    @Column(name = "address_bn", nullable = false)
    private String addressBn;
}
