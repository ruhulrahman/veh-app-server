package com.ibas.brta.vehims.acs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.acs.IService.ITransactionInfo;
import com.ibas.brta.vehims.acs.model.TransactionInfo;
import com.ibas.brta.vehims.acs.repository.TransactionInfoRepository;

/**
 * @author ashshakur.rahaman
 */
@Service
public class TransactionInfoService implements ITransactionInfo {

    @Autowired
    TransactionInfoRepository repository;

    public List<TransactionInfo> findAll() {
        return repository.findAll();
    }

    public Optional<TransactionInfo> findById(Long id) {
        return repository.findById(id);
    }

    public TransactionInfo save(TransactionInfo transactionInfo) {
        return repository.save(transactionInfo);
    }

    public Optional<TransactionInfo> findByTransactionId(String transactionId) {
        return repository.findByTransactionIdEquals(transactionId);
    }

}
