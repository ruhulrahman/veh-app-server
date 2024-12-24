package com.ibas.brta.vehims.vehicle.model;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.common.model.audit.DateAudit;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "v_service_medias")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VServiceMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "v_service_media_id")
    private Long id;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "document_type_id")
    private Long documentTypeId;

    @Column(name = "v_service_request_id")
    private Long serviceRequestId;

    @Column(name = "vehicle_info_id")
    private Long vehicleInfoId;

}
