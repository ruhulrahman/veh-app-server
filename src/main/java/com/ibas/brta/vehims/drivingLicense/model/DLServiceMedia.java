package com.ibas.brta.vehims.drivingLicense.model;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.common.model.audit.DateAudit;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "dl_service_medias")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DLServiceMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dl_service_media_id")
    private Long id;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "document_type_id")
    private Long documentTypeId;

    @Column(name = "dl_service_request_id")
    private Long serviceRequestId;

    @Column(name = "dl_info_id")
    private Long dlInfoId;

}
