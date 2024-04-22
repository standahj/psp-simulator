package com.stanhejny.psp.component;

import com.stanhejny.psp.model.PersistenceRecord;
import com.stanhejny.psp.model.TransactionStatus;

import java.util.stream.Stream;

/**
 * API for the Storage Provider
 */
public interface StorageProvider {
    /**
     * Used for initial insert of the transaction to the DB.
     * @param persistenceRecord  record to store.
     * @return copy of the stored record.
     */
    PersistenceRecord store(PersistenceRecord persistenceRecord);

    /**
     * Retrieve stored transaction details.
     * @param transactionId ID to retrieve.
     * @return Stored record or NULL if no such record exists.
     */
    PersistenceRecord get(String transactionId);

    /**
     * Update the transaction status.
     * @param transactionId  Transaction ID to update.
     * @param transactionStatus new status vaue.
     * @return copy of the record containing the updated value.
     */
    PersistenceRecord updateStatus(String transactionId, TransactionStatus transactionStatus, String decisionTransactionId);

    /**
     * Convenience method for testing to list all received transactions.
     * @return List of stored records.
     */
    Stream<PersistenceRecord> listAllTransactions();
}
