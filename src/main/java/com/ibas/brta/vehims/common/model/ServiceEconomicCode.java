package com.ibas.brta.vehims.common.model;

import com.ibas.brta.vehims.common.model.audit.DateAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "service_economic_codes")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "service_economic_code_id"))

public class ServiceEconomicCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "operation_code")
    private String operationCode;

    @Column(name = "operation_name")
    private String operationName;

    @Column(name = "fund_code")
    private String fundCode;

    @Column(name = "economic_code")
    private String economicCode;

    @Column(name = "economic_description_en")
    private String economicDescriptionEn;

}
