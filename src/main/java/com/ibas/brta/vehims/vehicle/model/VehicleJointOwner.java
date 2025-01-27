package com.ibas.brta.vehims.vehicle.model;

import java.util.Date;

import com.ibas.brta.vehims.common.model.audit.DateAudit;
import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "v_vehicle_joint_owners")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "vehicle_joint_owner_id"))

public class VehicleJointOwner extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle Info ID is required")
    @Column(name = "vehicle_info_id", nullable = false)
    private Long vehicleInfoId;

    @NotNull(message = "Service Request ID is required")
    @Column(name = "v_service_request_id", nullable = false)
    private Long serviceRequestId;

    @Column(name = "vehicle_owner_id")
    private Long vehicleOwnerId;

    @NotBlank(message = "Joint Owner Name is required")
    @Size(max = 100, message = "Joint Owner Name can be up to 100 characters long")
    @Column(name = "joint_owner_name", nullable = false, length = 100)
    private String jointOwnerName;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "address_type_id")
    private Long addressTypeId;

    @NotNull(message = "Location ID is required")
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @NotBlank(message = "Post Code is required")
    @Size(max = 20, message = "Post Code can be up to 20 characters long")
    @Column(name = "post_code", nullable = false, length = 20)
    private String postCode;

    @Size(max = 100, message = "Holding House/Village can be up to 100 characters long")
    @Column(name = "holding_house_village", length = 100)
    private String holdingHouseVillage;

    @Size(max = 100, message = "Road/Block/Sector/Colony can be up to 100 characters long")
    @Column(name = "road_block_sector_colony", length = 100)
    private String roadBlockSectorColony;

    @Size(max = 20, message = "Mobile Number can be up to 20 characters long")
    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;
}
