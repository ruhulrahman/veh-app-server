package com.ibas.brta.vehims.acs.IService;

import java.util.List;
import java.util.Optional;

import com.ibas.brta.vehims.acs.model.TransactionInfo;

/**
 * @author ashshakur.rahaman
 */

public interface ITransactionInfo {

    List<TransactionInfo> findAll();

    Optional<TransactionInfo> findById(Long id);

    TransactionInfo save(TransactionInfo transactionInfo);

    public Optional<TransactionInfo> findByTransactionId(String transactionId);

}
