package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardExpiryDateValidatorTest {

    @Test
    void validateValid() {
        // given
        CardExpiryDateValidator validator = new CardExpiryDateValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setExpiryDate("07/99");

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void validateExpired() {
        // given
        CardExpiryDateValidator validator = new CardExpiryDateValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setExpiryDate("07/00");

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Credit Card has expired.", result.get());
    }

    @Test
    void validateEmpty() {
        // given
        CardExpiryDateValidator validator = new CardExpiryDateValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setExpiryDate("");

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("No valid expiry date provided.", result.get());
    }

    @Test
    void validateMissing() {
        // given
        CardExpiryDateValidator validator = new CardExpiryDateValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setExpiryDate(null);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("No valid expiry date provided.", result.get());
    }

    @Test
    void validateNotDate() {
        // given
        CardExpiryDateValidator validator = new CardExpiryDateValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setExpiryDate("77/77");

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("No valid expiry date provided.", result.get());
    }
}