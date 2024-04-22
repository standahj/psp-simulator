package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;

import java.util.Optional;

public class CardTransactionValidator implements Validator {

    private CardNumberValidator cardNumberValidator;
    private CardExpiryDateValidator cardExpiryDateValidator;
    private CardCvvValidator cardCvvValidator;
    private CardMandatoryValuesValidator cardMandatoryValuesValidator;

    public CardTransactionValidator() {
        this.cardNumberValidator = new CardNumberValidator();
        this.cardExpiryDateValidator = new CardExpiryDateValidator();
        this.cardCvvValidator = new CardCvvValidator();
        this.cardMandatoryValuesValidator = new CardMandatoryValuesValidator();
    }

    /**
     * Validate the Card Transaction, apply chain of validators, however stops at the first failure.
     * @param cardTransaction transaction to validate
     * @return Optional.empty() if all is valid, or an Optional with validation failure reason.
     */
    public Optional<String> validate(CardTransaction cardTransaction) {
        Optional<String> validationResult = cardNumberValidator.validate(cardTransaction)
                .or(() -> cardExpiryDateValidator.validate(cardTransaction))
                .or(() -> cardCvvValidator.validate(cardTransaction))
                .or(() -> cardMandatoryValuesValidator.validate(cardTransaction));
        return validationResult;
    }
}
