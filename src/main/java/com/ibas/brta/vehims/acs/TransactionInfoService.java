package com.ibas.brta.vehims.acs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ashshakur.rahaman
 */
@Service
public class TransactionInfoService implements ITransactionInfo {

    @Autowired
    TransactionInfoRepository repository; 

    public List<TransactionInfo> findAll(){
        return repository.findAll(); 
    }

    public Optional<TransactionInfo> findById(Long id){
        return repository.findById(id);
    }

    public TransactionInfo save(TransactionInfo transactionInfo){
        return repository.save(transactionInfo);
    }

    public Optional<TransactionInfo> findByTransactionId(String transactionId){
        return repository.findByTransactionIdEquals(transactionId); 
    }

    
}
