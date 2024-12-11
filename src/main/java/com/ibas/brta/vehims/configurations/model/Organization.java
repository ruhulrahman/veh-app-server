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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "c_organizations")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "org_id"))

public class Organization extends RecordAudit {

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

    @NotNull(message = "Office Type Id cannot be null")
    @Column(name = "office_type_id", nullable = false)
    private Long officeTypeId;

    @Column(name = "parent_org_id")
    private Long parentId;

    @NotNull(message = "Location Id cannot be null")
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @NotNull(message = "Post Code cannot be null")
    @Size(max = 20)
    @Column(name = "post_code", nullable = false)
    private String postCode;

    @NotNull(message = "Address in English cannot be null")
    @Size(max = 100)
    @Column(name = "address_en", nullable = false)
    private String addressEn;

    @NotNull(message = "Address in Bangla cannot be null")
    @Size(max = 100)
    @Column(name = "address_bn", nullable = false)
    private String addressBn;
}
