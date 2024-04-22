package com.stanhejny.psp.model;

import lombok.Getter;

/**
 * The response of the PSP system to merchant with card transaction authorization decision.
 */
@Getter
public class CardTransactionResponse {
    private String pspId;
    private String acquirerId;
    private TransactionStatus transactionStatus;
    private String maskedCardNumber;
    private String amount;
    private String currency;
    private String merchantId;

    /**
     * Initializes response from the PSP internal data structures.
     * @param localRecord transaction record stored in PSP database.
     * @param acquirerResponse response from acquirer service.
     */
    public CardTransactionResponse(PersistenceRecord localRecord, AcquirerResponse acquirerResponse) {
        this.pspId = localRecord.getTransactionId();;
        this.acquirerId = acquirerResponse.getTransactionId();
        this.transactionStatus = localRecord.getTransactionStatus();
        this.maskedCardNumber = acquirerResponse.getMaskedCardNumber();
        this.amount = localRecord.getCardTransaction().getAmount();
        this.currency = localRecord.getCardTransaction().getCurrency();
        this.merchantId = localRecord.getCardTransaction().getMerchantId();
    }
}
