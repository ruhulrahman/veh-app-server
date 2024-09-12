package com.ibas.brta.vehims.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibas.brta.vehims.security.UserPrincipal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * To manage and track audit information related to entity maker, modifier,
 * creation and modification times
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 *          v 1.1 08-21-24 Adding mechanism for auto UUID generation, and logged
 *          in user to be used for createdBy and updatedBy.
 */

@MappedSuperclass
@JsonIgnoreProperties(value = { "createdBy", "updatedBy" }, allowGetters = true)

@Data
@Accessors(chain = true)
@EnableJpaAuditing
@Embeddable
@EqualsAndHashCode(callSuper = false)
public abstract class RecordAudit extends DateAudit {

    // @Column(length = 36)
    // @NotNull
    // private UUID uuid = UUID.randomUUID(); // UUID generated in the constructor

    @CreatedBy
    @Column(name = "create_user_id")
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "update_user_id")
    private Long updatedBy;

    // Active (A) or Inactive (I) or Deleted (X)
    // @Column(name = "status", nullable = false, length = 1)
    // @NotBlank
    // private String status;

    @NotNull
    private Boolean isActive = true;

    /**
     * @return Object unique identifier for the object
     */
    // public Object getIdentifier() {
    // return getUuid();
    // }

    @PrePersist
    public void prePersist() {
        // setCreatedBy(getLoggedinUserId());
        Long createdUserId = getLoggedinUserId();
        this.createdBy = createdUserId;
    }

    @PreUpdate
    public void preUpdate() {
        // setUpdatedBy(getLoggedinUserId());
        Long updateUserId = getLoggedinUserId();
        this.updatedBy = updateUserId;
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
