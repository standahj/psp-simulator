package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;

import java.util.Optional;

/**
 * Validate the credit card number presence and length (AMEX - 15 digits, others - 16 digit)
 * and if correct, apply the Luhn algorithm to validate the number itself.
 */
public class CardNumberValidator implements Validator {

    private static final int STANDARD_CARD_NUMBER_LENGTH = 16;
    private static final int AMEX_CARD_NUMBER_LENGTH = 15;

    /**
     * Validate the Card Number.
     * @param cardTransaction transaction to validate
     * @return Optional.empty() if all is valid, or an Optional with validation failure reason.
     */
    @Override
    public Optional<String> validate(CardTransaction cardTransaction) {
        final int cardNumberLength = cardTransaction.isAmex() ? AMEX_CARD_NUMBER_LENGTH : STANDARD_CARD_NUMBER_LENGTH;
        final String cardNumber = cardTransaction.getCardNumber();
        if (cardNumber.length() != cardNumberLength) {
            return Optional.of("Card number has wrong length.");
        }
        return this.checkCardNumberLuhn(cardNumber) ? Optional.empty() : Optional.of("Credit card number is not valid.");
    }

    /**
     * Returns true if given card number is valid.
     */
    private boolean checkCardNumberLuhn(String cardNo)
    {
        String cardNumber = cardNo;
        int nDigits = cardNumber.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {
            int d = cardNumber.charAt(i) - '0';
            if (isSecond) {
                d = d * 2;
            }
            // Add two digits to handle cases that make two digits after doubling
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}
