package com.stanhejny.psp.component;

import com.stanhejny.psp.model.AcquirerResponse;
import com.stanhejny.psp.model.CardTransaction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The Web Client (a Proxy) that communicates with the payment acquirer service to authorize or deny the transaction.
 */
@Component
public class PaymentAcquirerClient {

    private final WebClient client;

    /**
     * Spring Boot auto-configures a `WebClient.Builder` instance with nice defaults and customizations.
     * Typically, it is just enough to configure the endpoint URL.
     */

    public PaymentAcquirerClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8080/mock/acquirer").build();
    }

    /**
     * Forward the transaction to acquirer service for authorization,
     * @param cardTransaction  - transaction to approve.
     * @return - the decision.
     */
    public Mono<AcquirerResponse> authorize(CardTransaction cardTransaction) {
        return this.client.post().uri("/authorize")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cardTransaction))
                .retrieve()
                .bodyToMono(AcquirerResponse.class);
    }
}
