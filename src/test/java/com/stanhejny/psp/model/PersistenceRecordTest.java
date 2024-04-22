package com.stanhejny.psp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceRecordTest {

    @Test
    void isInitializedCorrectly() {
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
        PersistenceRecord testSubject = new PersistenceRecord(cardTransaction);

        // then
        assertTrue(testSubject.getTransactionId().length() > 10);
        assertEquals(testSubject.getTransactionStatus(), TransactionStatus.PENDING);
        assertTrue(testSubject.getTimestamp() > 10000L);
        assertNotNull(testSubject.getCardTransaction());
    }

    @Test
    void isInitializedCorrectlyFailedValidation() {
        // given
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);
        cardTransaction.setValid(false);

        // when
        PersistenceRecord testSubject = new PersistenceRecord(cardTransaction);

        // then
        assertTrue(testSubject.getTransactionId().length() > 10);
        assertEquals(testSubject.getTransactionStatus(), TransactionStatus.FAILED_VALIDATION);
        assertTrue(testSubject.getTimestamp() > 10000L);
        assertNotNull(testSubject.getCardTransaction());
    }
}