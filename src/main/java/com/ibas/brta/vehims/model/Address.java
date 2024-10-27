package com.ibas.brta.vehims.model;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.model.audit.DateAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "s_addresses")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "address_id"))

public class Address extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Size(max = 255, message = "Model can be up to 255 characters long")
    @Column(name = "model", length = 255)
    private String model;

    @Column(name = "model_id")
    private Long modelId;

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
