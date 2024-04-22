package com.stanhejny.psp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTransactionResponseTest {

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

        PersistenceRecord localRecord = new PersistenceRecord();
        localRecord.setTransactionStatus(TransactionStatus.APPROVED);
        localRecord.setTimestamp(665544332200L);
        localRecord.setTransactionId("123abc");
        localRecord.setCardTransaction(cardTransaction);

        AcquirerResponse acquirerResponse = AcquirerResponse.builder()
                .decision("approved")
                .maskedCardNumber("************8888")
                .merchantId("1a2b3c")
                .transactionId("a1b2c3")
                .timestamp(665544332211L)
                .build();

        // when
        CardTransactionResponse testSubject = new CardTransactionResponse(localRecord, acquirerResponse);

        // then
        assertEquals("a1b2c3", testSubject.getAcquirerId());
        assertEquals("777.50", testSubject.getAmount());
        assertEquals("EUR", testSubject.getCurrency());
        assertEquals("************8888", testSubject.getMaskedCardNumber());
        assertEquals("123abc", testSubject.getPspId());
        assertEquals(TransactionStatus.APPROVED, testSubject.getTransactionStatus());
    }

}