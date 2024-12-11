package com.ibas.brta.vehims.userManagement.model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibas.brta.vehims.security.UserPrincipal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "s_user_organization_roles")

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "user_org_id"))

public class UserOfficeRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User Id cannot be null")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Organization Id cannot be null")
    @Column(name = "org_id", nullable = false)
    private Long orgId;

    @NotNull(message = "Role Id cannot be null")
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @CreatedBy
    @Column(name = "create_user_id")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        // setCreatedBy(getLoggedinUserId());
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
