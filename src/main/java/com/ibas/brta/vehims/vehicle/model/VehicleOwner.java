package com.ibas.brta.vehims.vehicle.model;

import java.util.Date;

import com.ibas.brta.vehims.common.model.audit.DateAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "v_vehicle_owners")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_owner_id"))

public class VehicleOwner extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle Info ID is required")
    @Column(name = "vehicle_info_id", nullable = false)
    private Long vehicleInfoId;

    @NotNull(message = "Service Request ID is required")
    @Column(name = "service_request_id", nullable = false)
    private Long serviceRequestId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can be up to 100 characters long")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Father or Husband Name is required")
    @Size(max = 100, message = "Father or Husband Name can be up to 100 characters long")
    @Column(name = "father_or_husband_name", nullable = false, length = 100)
    private String fatherOrHusbandName;

    @NotBlank(message = "Mother Name is required")
    @Size(max = 100, message = "Mother Name can be up to 100 characters long")
    @Column(name = "mother_name", nullable = false, length = 100)
    private String motherName;

    @NotNull(message = "Gender ID is required")
    @Column(name = "gender_id", nullable = false)
    private Long genderId;

    @NotNull(message = "Nationality ID is required")
    @Column(name = "nationality_id", nullable = false)
    private Long nationalityId;

    @Size(max = 100, message = "Guardian Name can be up to 100 characters long")
    @Column(name = "guardian_name", length = 100)
    private String guardianName;

    @Size(max = 100, message = "Passport No can be up to 100 characters long")
    @Column(name = "passport_no", length = 100)
    private String passportNo;

    @Size(max = 100, message = "Birth Certificate No can be up to 100 characters long")
    @Column(name = "birth_certificate_no", length = 100)
    private String birthCertificateNo;

    @NotNull(message = "Address ID is required")
    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @NotNull(message = "Is Recondition is required")
    @Column(name = "is_recondition", nullable = false)
    private Boolean isRecondition = false;

    @NotNull(message = "Is Joint Owner is required")
    @Column(name = "is_joint_owner", nullable = false)
    private Boolean isJointOwner = false;

    @NotNull(message = "Owner Type ID is required")
    @Column(name = "owner_type_id", nullable = false)
    private Long ownerTypeId;

    @Column(name = "ministry_id")
    private Long ministryId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "sub_office_unit_group_id")
    private Long subOfficeUnitGroupId;

    @Column(name = "unit_or_activity_id")
    private Long unitOrActivityId;

    @Column(name = "in_use")
    private Boolean inUse = true;

    @Column(name = "used_by_id")
    private Long usedById;

    @Column(name = "within_organogram")
    private Boolean withinOrganogram;

    @Column(name = "acquisition_process_id")
    private Long acquisitionProcessId;

    @Size(max = 255)
    @Column(name = "acquisition_office")
    private String acquisitionOffice;

    @Column(name = "acquisition_price")
    private Long acquisitionPrice;

    @Column(name = "date_of_receipt")
    @Temporal(TemporalType.DATE)
    private Date dateOfReceipt;

    @Size(max = 255)
    @Column(name = "remarks")
    private String remarks;

    @NotNull(message = "Is Primary Owner is required")
    @Column(name = "is_primary_owner", nullable = false)
    private Boolean isPrimaryOwner = true;
}
