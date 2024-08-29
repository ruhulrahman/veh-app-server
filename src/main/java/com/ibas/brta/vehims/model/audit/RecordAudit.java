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
 * To manage and track audit information related to entity maker, modifier, creation and modification times
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 * v 1.1 08-21-24 Adding mechanism for auto UUID generation, and logged in user to be used for createdBy and updatedBy.
 */

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)

@Data
@Accessors(chain = true)
@EnableJpaAuditing
@Embeddable
@EqualsAndHashCode(callSuper=false)
public abstract class RecordAudit extends DateAudit {

    @Column(length = 36)
    @NotNull
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")
    //private String uuid;

    private UUID uuid = UUID.randomUUID(); // UUID generated in the constructor

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;

    // Active (A) or Inactive (I) or Deleted (D)
    @Column(name = "rec_stat", nullable = false, length = 1)
    @NotBlank
    private String status;

    @NotNull
    private Boolean isActive = true;

//    public RecordAudit() {
//        this(UUID.randomUUID());
//    }
//
//    public RecordAudit(UUID guid) {
//        Assert.notNull(guid, "UUID is required.");
//        setUuid(guid.toString());
//    }

    /**
     * @return Object unique identifier for the object
     */
    public Object getIdentifier() {
        return getUuid();
    }

}
