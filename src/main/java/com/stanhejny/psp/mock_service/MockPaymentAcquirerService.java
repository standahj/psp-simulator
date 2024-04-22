package com.stanhejny.psp.mock_service;

import com.stanhejny.psp.mock_model.MockCardTransaction;
import com.stanhejny.psp.mock_model.MockResponse;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Simulation of the acquirer service.
 */
@Service
public class MockPaymentAcquirerService {

    private final static String APPROVED = "approved";
    private final static String DENIED = "denied";

    public MockPaymentAcquirerService() {
    }

    /**
     * Authorize card transaction where card number ends with even digit, denies for card number ending with odd digit.
     * @param request containing MockcCardTransaction data structure.
     * @return MockResponse with the decision.
     */
    public Mono<ServerResponse> authorize(@NonNull ServerRequest request) {
        var response = request.bodyToMono(MockCardTransaction.class).flatMap(transaction -> {
            String cardNumber = transaction.getCardNumber();
            String lastDigit = cardNumber.substring(cardNumber.length()-1);
            String decision = DENIED;
            if (Integer.parseInt(lastDigit) % 2 == 0) {
                decision = APPROVED;
            }
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(MockResponse.builder()
                            .transactionId(UUID.randomUUID().toString())
                            .merchantId(transaction.getMerchantId())
                            .maskedCardNumber("************" + transaction.getCardNumber().substring(transaction.getCardNumber().length() - 4))
                            .timestamp(System.currentTimeMillis())
                            .decision(decision)
                            .build());
        });
        return response;
    }
}
