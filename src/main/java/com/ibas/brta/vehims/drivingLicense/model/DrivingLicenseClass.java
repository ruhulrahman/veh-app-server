package com.ibas.brta.vehims.drivingLicense.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "s_dl_info_classes")
public class DrivingLicenseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dl_info_class_id", nullable = false)
    private Long id;

    @Column(name = "dl_service_request_id", nullable = true)
    private Long dlServiceRequestId;

    @Column(name = "dll_id", nullable = true)
    private Long dllId;

    @Column(name = "dl_info_id", nullable = true)
    private Long dlInfoId;

    @Column(name = "dl_class_id")
    private Long dlClassId;
}