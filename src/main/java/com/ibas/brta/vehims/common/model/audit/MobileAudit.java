package com.ibas.brta.vehims.common.model.audit;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "x_mobile_audit")
@NoArgsConstructor
public class MobileAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String oldMobile;

    @Column(nullable = false)
    private String newMobile;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    @Column(nullable = false)
    private String changedBy;

}