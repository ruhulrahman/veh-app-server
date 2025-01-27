package com.ibas.brta.vehims.userManagement.model;

import java.time.LocalDate;

import com.ibas.brta.vehims.common.model.audit.DateAudit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "s_user_nid_infos")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "user_nid_info_id"))

public class UserNidInfo extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "nid_number", nullable = false, length = 100)
    private String nidNumber;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "name_bn", nullable = false, length = 100)
    private String nameBn;

    @Column(name = "name_en", nullable = false, length = 100)
    private String nameEn;

    @Column(name = "father_or_husband_name_en", length = 100)
    private String fatherOrHusbandNameEn;

    @Column(name = "father_or_husband_name_bn", nullable = false, length = 100)
    private String fatherOrHusbandNameBn;

    @Column(name = "mother_name_en", length = 100)
    private String motherNameEn;

    @Column(name = "mother_name_bn", nullable = false, length = 100)
    private String motherNameBn;

    @Column(name = "address_en", length = 100)
    private String addressEn;

    @Column(name = "address_bn", nullable = false, length = 100)
    private String addressBn;

    @Column(name = "gender_id", nullable = false)
    private Long genderId;

    @Column(name = "mobile", nullable = false, length = 20)
    private String mobile;

    @Column(name = "photo")
    private String photo;

    @Column(name = "present_address")
    private String presentAddress;

    @Column(name = "permanent_address")
    private String permanentAddress;

}
