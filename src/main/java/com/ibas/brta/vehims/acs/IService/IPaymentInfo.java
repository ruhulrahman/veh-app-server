package com.ibas.brta.vehims.acs.IService;

import java.util.List;
import java.util.Optional;

import com.ibas.brta.vehims.acs.model.PaymentInfo;

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
