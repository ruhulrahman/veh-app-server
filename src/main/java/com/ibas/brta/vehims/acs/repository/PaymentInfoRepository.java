package com.ibas.brta.vehims.acs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.acs.model.PaymentInfo;

/**
 * @author ashshakur.rahaman
 */

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByPaymentIdEquals(String paymentId);

    Optional<PaymentInfo> findByTransactionIdEquals(String transactionId);
}
