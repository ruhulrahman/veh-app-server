package com.ibas.brta.vehims.acs;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ashshakur.rahaman
 */

@Repository
public interface TransactionInfoRepository extends JpaRepository<TransactionInfo, Long> {
    Optional<TransactionInfo> findByTransactionIdEquals(String transactionId); 
}
