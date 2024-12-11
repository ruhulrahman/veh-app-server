package com.ibas.brta.vehims.acs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.acs.IService.IPaymentInfo;
import com.ibas.brta.vehims.acs.model.PaymentInfo;
import com.ibas.brta.vehims.acs.repository.PaymentInfoRepository;

/**
 * @author ashshakur.rahaman
 */
@Service
public class PaymentInfoService implements IPaymentInfo {

    @Autowired
    PaymentInfoRepository repository;

    public Optional<PaymentInfo> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<PaymentInfo> findByPaymentId(String paymentId) {
        return repository.findByPaymentIdEquals(paymentId);
    }

    public Optional<PaymentInfo> findByTransactionId(String transactionId) {
        return repository.findByTransactionIdEquals(transactionId);
    }

    public List<PaymentInfo> findAll() {
        return repository.findAll();
    }

    public PaymentInfo save(PaymentInfo paymentInfo) {
        return repository.save(paymentInfo);
    }

}
