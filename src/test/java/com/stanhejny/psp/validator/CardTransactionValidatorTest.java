package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardTransactionValidatorTest {

    @Test
    void validateValidTransaction() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void validateWrongCardNumber() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400026");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Credit card number is not valid.", result.get());
    }

    @Test
    void validateWrongCVVNumber() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("CVV number has wrong length.", result.get());
    }

    @Test
    void validateWrongExpiryDate() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("47/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("No valid expiry date provided.", result.get());
    }

    @Test
    void validateEmptyAmount() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Transaction amount must be provided.", result.get());
    }

    @Test
    void validateMissingCurrency() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency(null);
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Transaction currency must be provided.", result.get());
    }

    @Test
    void validateMissingMerchantId() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId(null);
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Merchant ID must be provided.", result.get());
    }

    @Test
    void validateExpiredCard() {
        // given
        CardTransactionValidator validator = new CardTransactionValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/07");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Credit Card has expired.", result.get());
    }
}