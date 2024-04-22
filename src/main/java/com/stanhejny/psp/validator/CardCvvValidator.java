package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * CVV number validation, AMEX has 4 digits, other cards 3, and must be present on all transactions.
 */
public class CardCvvValidator implements Validator {

    private static final int STANDARD_CVV_NUMBER_LENGTH = 3;
    private static final int AMEX_CVV_NUMBER_LENGTH = 4;

    @Override
    public Optional<String> validate(CardTransaction cardTransaction) {
        final int cvvNumberLength = cardTransaction.isAmex() ? AMEX_CVV_NUMBER_LENGTH : STANDARD_CVV_NUMBER_LENGTH;
        if (!StringUtils.hasLength(cardTransaction.getCVV()) || cardTransaction.getCVV().length() != cvvNumberLength) {
            return Optional.of("CVV number has wrong length.");
        }
        return Optional.empty();
    }
}
