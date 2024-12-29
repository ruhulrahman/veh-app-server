package com.ibas.brta.vehims.configurations.model;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;
import com.ibas.brta.vehims.security.UserPrincipal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "v_service_document_maps")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "v_service_document_map_id"))

public class ServiceDocumentMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "document_type_id", nullable = false)
    private Long documentTypeId;

    @Column(name = "is_document_required", nullable = false)
    private Boolean isDocumentRequired = false;

    @Column(name = "priority")
    private Integer priority;

    @CreatedBy
    @Column(name = "create_user_id")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        Long createdUserId = getLoggedinUserId();
        this.createdBy = createdUserId;
    }

    private Long getLoggedinUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getId();
        }
        return null;
    }
}
