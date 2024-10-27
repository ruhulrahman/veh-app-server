package com.ibas.brta.vehims.model.drivingLicense;

import com.ibas.brta.vehims.model.audit.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "dl_informations")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Inheritance(strategy = InheritanceType.JOINED)
public class DLInformation extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dl_info_id")
    private Long dlInfoId;

    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

    @Column(name = "dl_number", length = 50)
    private String dlNumber;

    @Column(name = "dl_reference_number", length = 50)
    private String dlReferenceNumber;

    @Column(name = "dl_language_id", nullable = false)
    private Integer dlLanguageId;

    @Column(name = "application_type_id", nullable = false)
    private Integer applicationTypeId;

    @Column(name = "license_type_id", nullable = false)
    private Integer licenseTypeId;

    @Column(name = "blood_group_id", nullable = false)
    private Integer bloodGroupId;

    @Column(name = "dl_vehicle_class_id", nullable = false)
    private Integer dlVehicleClassId;

    @Column(name = "issued_office_id")
    private Integer issuedOfficeId;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @Column(name = "edu_qualification_id", nullable = false)
    private Integer eduQualificationId;

    @Column(name = "occupation_id", nullable = false)
    private Integer occupationId;

    @Column(name = "nationality_id", nullable = false)
    private Integer nationalityId;

    @Column(name = "marital_status_id", nullable = false)
    private Integer maritalStatusId;

    @Column(name = "spouse_name", length = 70)
    private String spouseName;

    @Column(name = "spouse_contact_no", length = 20)
    private String spouseContactNo;

    @Column(name = "is_other_cityzenship")
    private Boolean isOtherCitizenship;

    @Column(name = "office_phone_number", length = 20)
    private String officePhoneNumber;

    @Column(name = "residence_phone_number", length = 20)
    private String residencePhoneNumber;

    @Column(name = "emergency_contact_name", length = 70)
    private String emergencyContactName;

    @Column(name = "emergency_contact_number", length = 20)
    private String emergencyContactNumber;

    @Column(name = "emergency_contact_relationship_id", nullable = false)
    private Integer emergencyContactRelationshipId;

    @Column(name = "emergency_contact_email", length = 100)
    private String emergencyContactEmail;

    @Column(name = "present_address_id", nullable = false)
    private Integer presentAddressId;

    @Column(name = "permanent_address_id")
    private Integer permanentAddressId;

    @Column(name = "status_id")
    private Integer statusId;
}
