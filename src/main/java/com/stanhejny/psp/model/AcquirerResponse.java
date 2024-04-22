package com.stanhejny.psp.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Local (PSP) representation of the acquirer response. See also MockResponse, which produced by the acquirer service
 */
@Getter
@Builder
public class AcquirerResponse {
    private String transactionId;
    private String decision;
    private long timestamp;
    private String merchantId;
    private String maskedCardNumber;
}
