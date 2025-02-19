package com.ibas.brta.vehims.acs.repository;

import java.util.Optional;

import com.ibas.brta.vehims.vehicle.model.VehicleRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.acs.model.TransactionInfo;

/**
 * @author ashshakur.rahaman
 */

@Repository
public interface TransactionInfoRepository extends JpaRepository<TransactionInfo, Long> {
    Optional<TransactionInfo> findByTransactionIdEquals(String transactionId);

    @Query(value = "SELECT * FROM x_acs_txn_info WHERE service_request_no = :serviceRequestNo AND service_type = :serviceType", nativeQuery = true)
    Optional<TransactionInfo> findByServiceRequestNoAndServiceType(String serviceRequestNo, String serviceType);
}
