package com.stanhejny.psp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Record stored in PSP database.
 */
@Getter
@Setter
public class PersistenceRecord {
    private String transactionId;
    private String decisionTransactionId;
    private CardTransaction cardTransaction;
    private TransactionStatus transactionStatus;
    private long timestamp;

    /**
     * Initialize with sensible defaults.
     */
    public PersistenceRecord() {
        this.transactionId = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
        this.transactionStatus = TransactionStatus.PENDING;
    }

    /**
     * Instantiate and initialize the record for given card transaction.
     * @param cardTransaction card transaction after validation with flag indicating transaction validity.
     */
    public PersistenceRecord(CardTransaction cardTransaction) {
        this();
        this.cardTransaction = cardTransaction;
        if (!cardTransaction.isValid()) {
            this.transactionStatus = TransactionStatus.FAILED_VALIDATION;
        }
    }
}
