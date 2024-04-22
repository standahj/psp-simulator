package com.stanhejny.psp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response when the incoming transaction fails validation
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationFailureResponse {
    /**
     * Contains Human friendly validation failure reason.
     */
    private String validationFailure;
}
