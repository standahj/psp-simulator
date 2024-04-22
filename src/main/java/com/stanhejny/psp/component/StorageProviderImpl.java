package com.stanhejny.psp.component;

import com.stanhejny.psp.model.PersistenceRecord;
import com.stanhejny.psp.model.TransactionStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Implements the memory based (HashMap) Storage provider.
 */
@Component
public class StorageProviderImpl implements StorageProvider {

    private final Map<String, PersistenceRecord> cache = new HashMap<>();

    public StorageProviderImpl() {
    }

    public PersistenceRecord store(PersistenceRecord persistenceRecord) {
        this.cache.put(persistenceRecord.getTransactionId(), persistenceRecord);
        return persistenceRecord;
    }

    public PersistenceRecord get(String transactionId) {
        return this.cache.get(transactionId);
    }

    public synchronized PersistenceRecord updateStatus(String transactionId, TransactionStatus transactionStatus, String decisionTransactionId) {
        PersistenceRecord record = this.cache.get(transactionId);
        if (record != null) {
            record.setTransactionStatus(transactionStatus);
            record.setDecisionTransactionId(decisionTransactionId);
        }
        return record;
    }

    public Stream<PersistenceRecord> listAllTransactions() {
        return this.cache.values().stream();
    }
}
