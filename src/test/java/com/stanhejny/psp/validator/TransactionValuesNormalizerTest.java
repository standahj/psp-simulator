package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValuesNormalizerTest {

    @Test
    void normalizeAmexValues() {
        // given
        TransactionValuesNormalizer normalizer = new TransactionValuesNormalizer();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("3742-4545-5400-126");
        cardTransaction.setExpiryDate("7/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50 ");
        cardTransaction.setCurrency(" EUR");
        cardTransaction.setMerchantId(" 1a2b3c   ");

        // when
        normalizer.normalizeValues(cardTransaction);

        // then
        assertEquals("374245455400126", cardTransaction.getCardNumber());
        assertEquals("07/27", cardTransaction.getExpiryDate());
        assertEquals("7727", cardTransaction.getCVV());
        assertEquals("777.50", cardTransaction.getAmount());
        assertEquals("EUR", cardTransaction.getCurrency());
        assertEquals("1a2b3c", cardTransaction.getMerchantId());
        assertTrue(cardTransaction.isAmex());
    }

    @Test
    void normalizeMasterCardValues() {
        // given
        TransactionValuesNormalizer normalizer = new TransactionValuesNormalizer();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("5425 2334 3010 9903");
        cardTransaction.setExpiryDate("727");
        cardTransaction.setCVV("772 ");
        cardTransaction.setAmount(" 777.50");
        cardTransaction.setCurrency(" EUR ");
        cardTransaction.setMerchantId(" 1a2b3c ");

        // when
        normalizer.normalizeValues(cardTransaction);

        // then
        assertEquals("5425233430109903", cardTransaction.getCardNumber());
        assertEquals("07/27", cardTransaction.getExpiryDate());
        assertEquals("772", cardTransaction.getCVV());
        assertEquals("777.50", cardTransaction.getAmount());
        assertEquals("EUR", cardTransaction.getCurrency());
        assertEquals("1a2b3c", cardTransaction.getMerchantId());
        assertFalse(cardTransaction.isAmex());
    }

    @Test
    void normalizeVisaValues() {
        // given
        TransactionValuesNormalizer normalizer = new TransactionValuesNormalizer();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber(" 4263 9826 4026-9299 ");
        cardTransaction.setExpiryDate("7 27");
        cardTransaction.setCVV("772 ");
        cardTransaction.setAmount(" 777.50 ");
        cardTransaction.setCurrency(" EUR ");
        cardTransaction.setMerchantId(" 1a2b3c ");

        // when
        normalizer.normalizeValues(cardTransaction);

        // then
        assertEquals("4263982640269299", cardTransaction.getCardNumber());
        assertEquals("07/27", cardTransaction.getExpiryDate());
        assertEquals("772", cardTransaction.getCVV());
        assertEquals("777.50", cardTransaction.getAmount());
        assertEquals("EUR", cardTransaction.getCurrency());
        assertEquals("1a2b3c", cardTransaction.getMerchantId());
        assertFalse(cardTransaction.isAmex());
    }

    @Test
    void normalizeVisaValuesExpDateNoSlash() {
        // given
        TransactionValuesNormalizer normalizer = new TransactionValuesNormalizer();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber(" 4263 9826 4026-9299 ");
        cardTransaction.setExpiryDate("0727");
        cardTransaction.setCVV("772 ");
        cardTransaction.setAmount(" 777.50 ");
        cardTransaction.setCurrency(" EUR ");
        cardTransaction.setMerchantId(" 1a2b3c ");

        // when
        normalizer.normalizeValues(cardTransaction);

        // then
        assertEquals("4263982640269299", cardTransaction.getCardNumber());
        assertEquals("07/27", cardTransaction.getExpiryDate());
        assertEquals("772", cardTransaction.getCVV());
        assertEquals("777.50", cardTransaction.getAmount());
        assertEquals("EUR", cardTransaction.getCurrency());
        assertEquals("1a2b3c", cardTransaction.getMerchantId());
        assertFalse(cardTransaction.isAmex());
    }

    @Test
    void normalizeVisaValuesExpDateNoSlashButDot() {
        // given
        TransactionValuesNormalizer normalizer = new TransactionValuesNormalizer();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber(" 4263 9826 4026-9299 ");
        cardTransaction.setExpiryDate("07.27");
        cardTransaction.setCVV("772 ");
        cardTransaction.setAmount(" 777.50 ");
        cardTransaction.setCurrency(" EUR ");
        cardTransaction.setMerchantId(" 1a2b3c ");

        // when
        normalizer.normalizeValues(cardTransaction);

        // then
        assertEquals("4263982640269299", cardTransaction.getCardNumber());
        assertEquals("07/27", cardTransaction.getExpiryDate());
        assertEquals("772", cardTransaction.getCVV());
        assertEquals("777.50", cardTransaction.getAmount());
        assertEquals("EUR", cardTransaction.getCurrency());
        assertEquals("1a2b3c", cardTransaction.getMerchantId());
        assertFalse(cardTransaction.isAmex());
    }

    @Test
    void normalizeVisaDebitValues() {
        // given
        TransactionValuesNormalizer normalizer = new TransactionValuesNormalizer();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber(" 4701 3222 1111 1234 ");
        cardTransaction.setExpiryDate("7/2027");
        cardTransaction.setCVV("772 ");
        cardTransaction.setAmount(" 777.50 ");
        cardTransaction.setCurrency(" EUR ");
        cardTransaction.setMerchantId(" 1a2b3c ");

        // when
        normalizer.normalizeValues(cardTransaction);

        // then
        assertEquals("4701322211111234", cardTransaction.getCardNumber());
        assertEquals("07/27", cardTransaction.getExpiryDate());
        assertEquals("772", cardTransaction.getCVV());
        assertEquals("777.50", cardTransaction.getAmount());
        assertEquals("EUR", cardTransaction.getCurrency());
        assertEquals("1a2b3c", cardTransaction.getMerchantId());
        assertFalse(cardTransaction.isAmex());
    }
}