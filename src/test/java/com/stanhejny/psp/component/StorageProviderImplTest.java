package com.stanhejny.psp.component;

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
class StorageProviderImplTest {

    static StorageProvider storageProvider = new StorageProviderImpl();

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

        PersistenceRecord recordToStore = new PersistenceRecord(cardTransaction);

        // when
        PersistenceRecord storedRecord = storageProvider.store(recordToStore);

        // then
        Optional<PersistenceRecord> storedItem = storageProvider.listAllTransactions()
                .filter(persistenceRecord -> storedRecord.getTransactionId().equals(persistenceRecord.getTransactionId()))
                .findFirst();
        assertTrue(storedItem.isPresent());
    }

    @Order(2)
    @Test
    void get() {
        // given
        PersistenceRecord storedItem = storageProvider.listAllTransactions().findFirst().get();

        // when
        PersistenceRecord storedRecord = storageProvider.get(storedItem.getTransactionId());

        // then
        assertEquals(storedItem, storedRecord);
    }

    @Order(3)
    @Test
    void updateStatus() {
        // given
        PersistenceRecord storedItem = storageProvider.listAllTransactions().findFirst().get();

        // when
        storageProvider.updateStatus(storedItem.getTransactionId(), TransactionStatus.DENIED, "bbb222");

        // then
        PersistenceRecord storedRecord = storageProvider.get(storedItem.getTransactionId());
        assertEquals(storedRecord.getTransactionStatus(), TransactionStatus.DENIED);
        assertEquals("bbb222", storedRecord.getDecisionTransactionId());
    }

    @Order(4)
    @Test
    void listAllTransactions() {
        // given

        // when
        List<PersistenceRecord> cacheContent = storageProvider.listAllTransactions().collect(Collectors.toList());

        // then
        assertTrue(cacheContent.size() > 0);
    }
}