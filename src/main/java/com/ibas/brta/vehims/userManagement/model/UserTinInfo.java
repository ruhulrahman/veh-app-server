package com.ibas.brta.vehims.userManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "s_user_tin_infos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "user_tin_info_id"))
public class UserTinInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tin_number", nullable = false, length = 100)
    private String tinNumber;

    @Column(name = "tin_type", nullable = false)
    private String tinType;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;

    @Column(name = "deleted_date", nullable = false)
    private Date deletedDate;

    @Column(name = "assess_name", nullable = true)
    private String assessName;

    @Column(name = "nid", nullable = true)
    private String nid;

    @Column(name = "passport_number", nullable = true)
    private String passportNumber;

    @Column(name = "smart_id", length = 17, nullable = true)
    private String smartId;

    @Column(name = "father_name", length = 255, nullable = true)
    private String fatherName;

    @Column(name = "mother_name", length = 255, nullable = true)
    private String motherName;

    @Column(name = "mobile", length = 17, nullable = true)
    private String mobile;

    @Column(name = "email", length = 50, nullable = true)
    private String email;

    @Column(name = "dob", length = 10, nullable = true)
    private String dob;

    @Column(name = "nationality", length = 255, nullable = true)
    private String nationality;

    @Column(name = "zone_no", nullable = true)
    private Integer zoneNo;

    @Column(name = "zone_name", length = 255, nullable = true)
    private String zoneName;

    @Column(name = "circle_no", nullable = true)
    private Integer circleNo;

    @Column(name = "circle_name", length = 17, nullable = true)
    private String circleName;

    @Column(name = "status", length = 17, nullable = true)
    private String status;

    @Version
    @Column(name = "version_no")
    private Integer version;
}
