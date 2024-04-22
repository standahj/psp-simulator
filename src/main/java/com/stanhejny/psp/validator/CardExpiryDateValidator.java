package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Verifies expiry date presence and if card is not expired.
 */
public class CardExpiryDateValidator implements Validator {

    static final DateTimeFormatter EXPIRY_DATE = DateTimeFormatter.ofPattern("MM/yy");

    @Override
    public Optional<String> validate(CardTransaction cardTransaction) {
        try {
            YearMonth ym = YearMonth.parse(cardTransaction.getExpiryDate(), EXPIRY_DATE);
            if (ym.atEndOfMonth().isBefore(LocalDate.now())) {
                return Optional.of("Credit Card has expired.");
            }
            return Optional.empty();
        } catch (Exception parsingException) {
            return Optional.of("No valid expiry date provided.");
        }
    }
}
