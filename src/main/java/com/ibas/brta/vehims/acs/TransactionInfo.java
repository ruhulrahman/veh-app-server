package com.ibas.brta.vehims.acs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * @author ashshakur.rahaman
 */

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "x_acs_txn_info")
@EntityListeners(AuditingEntityListener.class)
public class TransactionInfo extends AbstractPersistable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "bearer_token", nullable=true)
    String bearerToken;

    @Column(name = "transaction_id", nullable=true)
    String transactionId;
   
}
