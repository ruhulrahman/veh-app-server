package com.ibas.brta.vehims.acs;

import java.util.List;
import java.util.Optional;

/**
 * @author ashshakur.rahaman
 */

public interface IPaymentInfo {

    List<PaymentInfo> findAll();

    Optional<PaymentInfo> findById(Long id);

    PaymentInfo save(PaymentInfo paymentInfo);

    Optional<PaymentInfo> findByPaymentId(String paymentId);

    public Optional<PaymentInfo> findByTransactionId(String transactionId);

}
