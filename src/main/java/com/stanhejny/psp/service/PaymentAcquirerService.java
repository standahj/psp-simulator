package com.stanhejny.psp.service;

import com.stanhejny.psp.component.PaymentAcquirerClient;
import com.stanhejny.psp.model.CardTransaction;
import com.stanhejny.psp.model.CardTransactionResponse;
import com.stanhejny.psp.model.TransactionStatus;
import com.stanhejny.psp.model.ValidationFailureResponse;
import com.stanhejny.psp.validator.CardTransactionValidator;
import com.stanhejny.psp.validator.TransactionValuesNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * PSP service simulator implementing the 'authorize' and 'list' endpoints handlers.
 */
@Service
public class PaymentAcquirerService {

    private PersistenceService persistenceService;
    private PaymentAcquirerClient paymentAcquirerClient;
    private TransactionValuesNormalizer transactionValuesNormalizer;
    private CardTransactionValidator cardTransactionValidator;

    @Autowired
    public PaymentAcquirerService(PersistenceService persistenceService, PaymentAcquirerClient paymentAcquirerClient) {
        this.persistenceService = persistenceService;
        this.paymentAcquirerClient = paymentAcquirerClient;
        this.transactionValuesNormalizer = new TransactionValuesNormalizer();
        this.cardTransactionValidator = new CardTransactionValidator();
    }

    /**
     * Handler for the '/authorize' endpoint to authorize incoming card transaction.
     * @param request contains CardTransaction data structure.
     * @return response containing CardTransactionResponse data structure.
     */
    public Mono<ServerResponse> authorizeTransaction(@NonNull ServerRequest request) {
        return request.bodyToMono(CardTransaction.class)
                .flatMap(cardTransaction ->
                {
                    this.transactionValuesNormalizer.normalizeValues(cardTransaction);
                    return this.cardTransactionValidator.validate(cardTransaction)
                            .map(errorMessage -> {
                                cardTransaction.setValid(false);
                                return ServerResponse.unprocessableEntity().contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(new ValidationFailureResponse(errorMessage));
                            })
                            .orElse(this.persistenceService.store(cardTransaction)
                                .flatMap(persistenceRecord ->
                                    this.paymentAcquirerClient.authorize(persistenceRecord.getCardTransaction())
                                            .flatMap(acquirerResponse -> {
                                                TransactionStatus status = persistenceRecord.getTransactionStatus();
                                                if ("approved".equalsIgnoreCase(acquirerResponse.getDecision())) {
                                                    status = TransactionStatus.APPROVED;
                                                } else if ("denied".equalsIgnoreCase(acquirerResponse.getDecision())) {
                                                    status = TransactionStatus.DENIED;
                                                }
                                                return this.persistenceService
                                                        .updateStatus(persistenceRecord.getTransactionId(), status, acquirerResponse.getTransactionId())
                                                        .flatMap(updatedRecord ->
                                                                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                                                        .bodyValue(new CardTransactionResponse(updatedRecord, acquirerResponse)));
                                            })
                                ));
                });
    }

    /**
     * List of all database content
     * @param request - not used, no input required.
     * @return list of database records.
     */
    public Mono<ServerResponse> listAllTransactions(@NonNull ServerRequest request) {
        return this.persistenceService.listAllTransactions().collectList().flatMap(transactionList ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionList));
    }
}
