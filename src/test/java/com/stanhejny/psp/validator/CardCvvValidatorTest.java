package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardCvvValidatorTest {

    @Test
    void validateAmexValid() {
        // given
        CardCvvValidator validator = new CardCvvValidator();
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
    void validateAmexTooLong() {
        // given
        CardCvvValidator validator = new CardCvvValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCVV("77273");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("CVV number has wrong length.", result.get());
    }

    @Test
    void validateVisaValid() {
        // given
        CardCvvValidator validator = new CardCvvValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCVV("772");
        cardTransaction.setAmex(false);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void validateVisaTooLong() {
        // given
        CardCvvValidator validator = new CardCvvValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCVV("7727");
        cardTransaction.setAmex(false);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("CVV number has wrong length.", result.get());
    }

    @Test
    void validateVisaEmpty() {
        // given
        CardCvvValidator validator = new CardCvvValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCVV("");
        cardTransaction.setAmex(false);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("CVV number has wrong length.", result.get());
    }

    @Test
    void validateVisaMissing() {
        // given
        CardCvvValidator validator = new CardCvvValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCVV(null);
        cardTransaction.setAmex(false);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("CVV number has wrong length.", result.get());
    }
}