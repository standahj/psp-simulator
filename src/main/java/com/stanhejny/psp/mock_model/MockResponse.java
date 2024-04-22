package com.stanhejny.psp.mock_model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response data structure containing decision of the acquirer about the card transaction.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockResponse {
    /**
     * the acquirer internal transaction ID (to back-track the decision, if disputed)
     */
    private String transactionId;
    /**
     * The decision, one of "approved" or "denied" values.
     */
    private String decision;
    /**
     * Timestamp of the decision
     */
    private long timestamp;
    /**
     * value provided in input to assist transaction identification.
     */
    private String merchantId;
    /**
     * Masked value to be included in the response.
     */
    private String maskedCardNumber;
}
