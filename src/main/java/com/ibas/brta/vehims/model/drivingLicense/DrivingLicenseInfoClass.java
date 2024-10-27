package com.ibas.brta.vehims.model.drivingLicense;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "s_dl_info_classes")
public class DrivingLicenseInfoClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dl_info_class_id", nullable = false)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "dl_info_id")
//    private com.ibas.brta.vehims.DlInformation dlInfo;

    @Column(name = "dl_info_id", nullable = false)
    private Long dlInfoId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "dl_class_id")
//    private Status dlClass;
    @Column(name = "dl_class_id")
    private Long dlClassId;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdAt;

}