package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardMandatoryValuesValidatorTest {

    @Test
    void validateValid() {
        // given
        CardMandatoryValuesValidator validator = new CardMandatoryValuesValidator();
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
    void validateEmpty() {
        // given
        CardMandatoryValuesValidator validator = new CardMandatoryValuesValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setAmount("");

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Transaction amount must be provided.", result.get());
    }

    @Test
    void validateMissing() {
        // given
        CardMandatoryValuesValidator validator = new CardMandatoryValuesValidator();
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setAmount("1");

        // when
        Optional<String> result = validator.validate(cardTransaction);

        // then
        assertTrue(result.isPresent());
        assertEquals("Transaction currency must be provided.", result.get());
    }
}