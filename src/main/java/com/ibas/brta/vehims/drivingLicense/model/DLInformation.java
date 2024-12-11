package com.ibas.brta.vehims.drivingLicense.model;

import com.ibas.brta.vehims.common.model.audit.DateAudit;
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
    private Long id;

    @Column(name = "nid_number", nullable = false)
    private String nidNumber;

    @Column(name = "dl_number", length = 50)
    private String dlNumber;

    @Column(name = "dl_reference_number", length = 50)
    private String dlReferenceNumber;

    @Column(name = "dl_language_id", nullable = false)
    private Long dlLanguageId;

    @Column(name = "application_type_id", nullable = false)
    private Long applicationTypeId;

    @Column(name = "applicant_type_id", nullable = false)
    private Long applicantTypeId;

    @Column(name = "license_type_id", nullable = false)
    private Long licenseTypeId;

    @Column(name = "blood_group_id", nullable = false)
    private Long bloodGroupId;

    @Column(name = "issued_office_id")
    private Long issuedOfficeId;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @Column(name = "edu_qualification_id", nullable = false)
    private Long eduQualificationId;

    @Column(name = "occupation_id", nullable = false)
    private Long occupationId;

    @Column(name = "nationality_id", nullable = false)
    private Long nationalityId;

    @Column(name = "marital_status_id", nullable = false)
    private Long maritalStatusId;

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
    private Long emergencyContactRelationshipId;

    @Column(name = "emergency_contact_email", length = 100)
    private String emergencyContactEmail;

    @Column(name = "present_address_id", nullable = false)
    private Long presentAddressId;

    @Column(name = "permanent_address_id")
    private Long permanentAddressId;

    @Column(name = "cd_option_id") // Card deliery optoin id
    private Long cdOptionId;

    @Column(name = "cd_address_id")
    private Long cardDeliveryAddressId;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "is_active")
    private Boolean isActive = false;
}
