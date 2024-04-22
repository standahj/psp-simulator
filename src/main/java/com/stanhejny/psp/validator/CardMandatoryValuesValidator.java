package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Verifies presence of mandatory fields for which there's no other validation logic available.
 */
public class CardMandatoryValuesValidator implements Validator {

    @Override
    public Optional<String> validate(CardTransaction cardTransaction) {
        if (!StringUtils.hasLength(cardTransaction.getAmount())) {
            return Optional.of("Transaction amount must be provided.");
        }
        if (!StringUtils.hasLength(cardTransaction.getCurrency())) {
            return Optional.of("Transaction currency must be provided.");
        }
        if (!StringUtils.hasLength(cardTransaction.getMerchantId())) {
            return Optional.of("Merchant ID must be provided.");
        }
        return Optional.empty();
    }
}
