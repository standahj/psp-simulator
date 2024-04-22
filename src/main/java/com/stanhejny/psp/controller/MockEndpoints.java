package com.stanhejny.psp.controller;

import com.stanhejny.psp.mock_service.MockPaymentAcquirerService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Routing bean for the simulated acquirer service, defining an end-point for authorizing card transactions.
 */
@Controller
public class MockEndpoints {

    @Bean
    RouterFunction<ServerResponse> mockApiRoutes(MockPaymentAcquirerService mockPaymentAcquirerService) {
        return nest(
                path("/mock/acquirer"),
                route()
                        .POST("/authorize", mockPaymentAcquirerService::authorize)
                        .build()
        );
    }
}
