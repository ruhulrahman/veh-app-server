package com.ibas.brta.vehims.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.time.Instant;

/**
 * To manage and track audit information related to entity creation and modification times
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial version.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Data
@Accessors(chain = true)
@EnableJpaAuditing
@Embeddable
public abstract class DateAudit implements Serializable {

    @CreatedDate
    @Column(name ="created_date")
    private Instant createdAt;

    @LastModifiedDate
    @Column (name ="updated_date")
    private Instant updatedAt;

    @Version
    @Column (name = "version_no")
    private int version;

}
