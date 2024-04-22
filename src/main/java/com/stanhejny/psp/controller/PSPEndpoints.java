package com.stanhejny.psp.controller;

import com.stanhejny.psp.service.PaymentAcquirerService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Routing bean for the payment service, defining an end-point for authorizing card transactions,
 * and convenience test end-point to list all received transactions.
 */
@Controller
public class PSPEndpoints {

    @Bean
    RouterFunction<ServerResponse> apiRoutes(PaymentAcquirerService paymentAcquirerService) {
        return nest(
                path("/api/v1/psp"),
                route()
                        .POST("/authorize", paymentAcquirerService::authorizeTransaction)
                        .GET("/list", paymentAcquirerService::listAllTransactions)
                        .build()
        );
    }
}
