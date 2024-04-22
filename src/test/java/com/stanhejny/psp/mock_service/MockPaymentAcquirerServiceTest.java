package com.stanhejny.psp.mock_service;

import com.stanhejny.psp.mock_model.MockCardTransaction;
import com.stanhejny.psp.mock_model.MockResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MockPaymentAcquirerServiceTest {

    ServerRequest mockRequest = Mockito.mock(ServerRequest.class);

    @Test
    void authorizeApprove() {
        // given
        MockCardTransaction mockCardTransaction = new MockCardTransaction();
        mockCardTransaction.setCardNumber("1111222233334444");
        Mockito.when(mockRequest.bodyToMono(MockCardTransaction.class)).thenReturn(Mono.just(mockCardTransaction));
        MockPaymentAcquirerService testSubject = new MockPaymentAcquirerService();

        // when
        Mono<ServerResponse> response = testSubject.authorize(mockRequest);

        // then
        ServerResponse serverResponse = response.block();
        assertEquals(serverResponse.statusCode(), HttpStatus.OK);
        try {
            Method entityMethod = serverResponse.getClass().getMethod("entity");
            entityMethod.setAccessible(true);
            Object entity = entityMethod.invoke(serverResponse);
            assertEquals(entity.getClass(), MockResponse.class);
            assertEquals("approved", ((MockResponse)entity).getDecision());
        } catch (Exception ex) {
            assertEquals(0, 1);
        }
    }

    @Test
    void authorizeDeny() {
        // given
        MockCardTransaction mockCardTransaction = new MockCardTransaction();
        mockCardTransaction.setCardNumber("1111222233334445");
        Mockito.when(mockRequest.bodyToMono(MockCardTransaction.class)).thenReturn(Mono.just(mockCardTransaction));
        MockPaymentAcquirerService testSubject = new MockPaymentAcquirerService();

        // when
        Mono<ServerResponse> response = testSubject.authorize(mockRequest);

        // then
        ServerResponse serverResponse = response.block();
        assertEquals(serverResponse.statusCode(), HttpStatus.OK);
        try {
            Method entityMethod = serverResponse.getClass().getMethod("entity");
            entityMethod.setAccessible(true);
            Object entity = entityMethod.invoke(serverResponse);
            assertEquals(entity.getClass(), MockResponse.class);
            assertEquals("denied", ((MockResponse)entity).getDecision());
        } catch (Exception ex) {
            assertEquals(0, 1);
        }
    }
}