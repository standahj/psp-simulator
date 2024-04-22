package com.stanhejny.psp.mock_model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Input data structure for the simulated acquirer service, representing card transaction to authorize.
 */
@Getter
@Setter
@NoArgsConstructor
public class MockCardTransaction {
    private String cardNumber;
    private String expiryDate;
    private String CVV;
    private String amount;
    private String currency;
    private String merchantId;
}
