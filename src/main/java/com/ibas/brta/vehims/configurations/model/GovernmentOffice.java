package com.ibas.brta.vehims.configurations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "c_government_offices")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@AttributeOverride(name = "id", column = @Column(name = "government_office_id"))

public class GovernmentOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_code")
    private String fullCode;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_bn")
    private String nameBn;

    @Column(name = "status")
    private String status;

    @Column(name = "parent_code")
    private String parentCode;

    // @Column(name = "created_date")
    // private Date createdDate;

    // @Column(name = "updated_date")
    // private Date updatedDate;
}
