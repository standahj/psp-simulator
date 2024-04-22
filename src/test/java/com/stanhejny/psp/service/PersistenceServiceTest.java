package com.stanhejny.psp.service;

import com.stanhejny.psp.component.StorageProvider;
import com.stanhejny.psp.component.StorageProviderImpl;
import com.stanhejny.psp.model.CardTransaction;
import com.stanhejny.psp.model.PersistenceRecord;
import com.stanhejny.psp.model.TransactionStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersistenceServiceTest {

    static StorageProvider memCache = new StorageProviderImpl();
    static PersistenceService persistenceService = new PersistenceService(memCache);

    @Order(1)
    @Test
    void store() {
        // given

        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Mono<PersistenceRecord> record = persistenceService.store(cardTransaction);

        // then
        PersistenceRecord localRecord = record.block();
        Optional<PersistenceRecord> storedItem = memCache.listAllTransactions()
                .filter(persistenceRecord -> localRecord.getTransactionId().equals(persistenceRecord.getTransactionId()))
                        .findFirst();
        assertTrue(storedItem.isPresent());
    }

    @Order(2)
    @Test
    void updateStatus() {
        // given
        PersistenceRecord storedItem = memCache.listAllTransactions().findFirst().get();

        // when
        this.persistenceService.updateStatus(storedItem.getTransactionId(), TransactionStatus.DENIED, "bbb222");

        // then
        Optional<PersistenceRecord> updatedItem = memCache.listAllTransactions()
                .filter(persistenceRecord -> persistenceRecord.getTransactionStatus() == TransactionStatus.DENIED)
                .findFirst();
        assertTrue(updatedItem.isPresent());
    }

    @Order(3)
    @Test
    void listAllTransactions() {
        // given

        // when
        List<PersistenceRecord> cacheContent = memCache.listAllTransactions().collect(Collectors.toList());

        // then
        assertTrue(cacheContent.size() > 0);
    }
}