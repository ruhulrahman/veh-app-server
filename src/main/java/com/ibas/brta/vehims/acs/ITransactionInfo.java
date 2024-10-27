package com.ibas.brta.vehims.acs;

import java.util.List;
import java.util.Optional;

/**
 * @author ashshakur.rahaman
 */

public interface ITransactionInfo {

    List<TransactionInfo> findAll();

    Optional<TransactionInfo> findById(Long id);

    TransactionInfo save(TransactionInfo transactionInfo);

    public Optional<TransactionInfo> findByTransactionId(String transactionId);

}
