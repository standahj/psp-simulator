package com.stanhejny.psp.service;

import com.stanhejny.psp.component.StorageProvider;
import com.stanhejny.psp.model.CardTransaction;
import com.stanhejny.psp.model.PersistenceRecord;
import com.stanhejny.psp.model.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service that provides communication proxy with the storage provider, transforming the data, implementing protocol specific items.
 * Note: For this example, it just passes the data to the StorageProvider instance.
 * Real storage provider will likely include reactive API already, here I wrap my own simulation in Mono/Flux as needed
 * to emulate the reactive API.
 */
@Service
public class PersistenceService {

    private final StorageProvider storageProvider;

    @Autowired
    public PersistenceService(StorageProvider storageProvider) {
        this.storageProvider = storageProvider;
    }

    public Mono<PersistenceRecord> store(CardTransaction cardTransaction) {
        return Mono.just(this.storageProvider.store(new PersistenceRecord(cardTransaction)));
    }

    public Mono<PersistenceRecord> updateStatus(String transactionId, TransactionStatus transactionStatus, String decisionTransactionId) {
        return Mono.just(this.storageProvider.updateStatus(transactionId, transactionStatus, decisionTransactionId));
    }

    public Flux<PersistenceRecord> listAllTransactions() {
        return Flux.fromStream(this.storageProvider.listAllTransactions());
    }
}
