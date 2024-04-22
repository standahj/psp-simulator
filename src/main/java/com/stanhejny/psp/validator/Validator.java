package com.stanhejny.psp.validator;

import com.stanhejny.psp.model.CardTransaction;

import java.util.Optional;

public interface Validator {
    Optional<String> validate(CardTransaction cardTransaction);
}
