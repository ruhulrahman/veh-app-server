package com.ibas.brta.vehims.acs;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author ashshakur.rahaman
 */

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
    List<PaymentDetails> findByTinEquals(String tin);
    Optional<PaymentDetails> findByPaymentId(String paymentId);
    Optional<PaymentDetails> findByChallanNo(String challanNo); 
}