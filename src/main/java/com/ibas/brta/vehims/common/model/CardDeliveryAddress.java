package com.ibas.brta.vehims.common.model;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.common.model.audit.DateAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "s_cd_addresses")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "cd_address_id"))

public class CardDeliveryAddress extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "address_type_id", nullable = false)
    private Long addressTypeId;

    @Column(name = "holding_house_village", length = 100, nullable = false)
    private String holdingHouseVillage;

    @Column(name = "road_block_sector_colony", length = 100)
    private String roadBlockSectorColony;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "post_code", length = 20, nullable = false)
    private String postCode;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "card_status_id")
    private Long cardStatusId;

    @Column(name = "delivery_status_id")
    private Long deliveryStatusId;

    @Column(name = "delivered_user_id")
    private Long deliveredUserId;

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;

}
