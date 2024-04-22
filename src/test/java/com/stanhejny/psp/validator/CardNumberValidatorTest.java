package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardNumberValidatorTest {

    @Test
    void validateValidAmex() {
        // given
        CardNumberValidator validator = new CardNumberValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void validateWrongAmex() {
        // given
        CardNumberValidator validator = new CardNumberValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245555400126");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Credit card number is not valid.", result.get());
    }

    @Test
    void validateTooLongAmex() {
        // given
        CardNumberValidator validator = new CardNumberValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("3742454554001261");
        cardTransaction.setAmex(true);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Card number has wrong length.", result.get());
    }

    @Test
    void validateValidVisa() {
        // given
        CardNumberValidator validator = new CardNumberValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("4263982640269299");
        cardTransaction.setAmex(false);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void validateWrongVisa() {
        // given
        CardNumberValidator validator = new CardNumberValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("4263982640269999");
        cardTransaction.setAmex(false);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Credit card number is not valid.", result.get());
    }

    @Test
    void validateTooShortVisa() {
        // given
        CardNumberValidator validator = new CardNumberValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("426398264026929");
        cardTransaction.setAmex(false);

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Card number has wrong length.", result.get());
    }
}