package com.ibas.brta.vehims.userManagement.model;

import java.time.LocalDate;

import com.ibas.brta.vehims.common.model.audit.DateAudit;
import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "s_user_details")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "user_detail_id"))

public class UserDetail extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "nid_number", length = 100)
    private String nidNumber;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "father_or_husband_name_en", length = 100)
    private String fatherOrHusbandNameEn;

    @Column(name = "father_or_husband_name_bn", length = 100)
    private String fatherOrHusbandNameBn;

    @Column(name = "mother_name_en", length = 100)
    private String motherNameEn;

    @Column(name = "mother_name_bn", length = 100)
    private String motherNameBn;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "present_address_id")
    private Long presentAddressId;

    @Column(name = "permanent_address_id")
    private Long permanentAddressId;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "birth_reg_number")
    private String birthRegNumber;

    // @Column(name = "photo")
    // private String photo;

    @Column(name = "photo_media_id")
    private Long photoMediaId;

    @Column(name = "blood_group_id")
    private Long bloodGroupId;

}
