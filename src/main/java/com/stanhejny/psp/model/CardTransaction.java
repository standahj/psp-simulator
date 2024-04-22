package com.stanhejny.psp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PSP input data structure with card transaction details.
 */
@Getter
@Setter
@NoArgsConstructor
public class CardTransaction {
    private String cardNumber;
    private String expiryDate;
    @JsonProperty("CVV")
    private String CVV;
    private String amount;
    private String currency;
    private String merchantId;
    @JsonIgnore
    private boolean isAmex;
    @JsonIgnore
    private boolean isValid = true;
}
