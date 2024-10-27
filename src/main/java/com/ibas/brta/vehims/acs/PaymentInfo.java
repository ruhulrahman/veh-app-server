package com.ibas.brta.vehims.acs;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author ashshakur.rahaman
 */

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "x_acs_payment_info")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@DynamicUpdate
@Description("Prepare payment info to get confirmation.")
public class PaymentInfo extends AbstractPersistable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "bearer_token", nullable=true)
    String bearerToken;
    
    @Column(name = "challan_no", nullable=true)
    String challanNo;
    
    @Column(name = "payment_id", unique = true)
    String paymentId;

    @Column(name = "paid_amount", nullable=true)
    String paidAmount;

    @Column(name = "transaction_id", nullable=true)
    String transactionId;

    @Column(name = "client_name", nullable=true)
    String clientName;

}
