package com.ibas.brta.vehims.model.configurations;

import java.time.LocalDate;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_fiscal_years")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "fiscal_year_id"))

public class FiscalYear extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;

    @NotNull(message = "Start Date cannot be blank")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End Date cannot be blank")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
