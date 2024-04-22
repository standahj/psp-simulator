package com.stanhejny.psp.service;

import com.stanhejny.psp.component.PaymentAcquirerClient;
import com.stanhejny.psp.model.AcquirerResponse;
import com.stanhejny.psp.model.CardTransaction;
import com.stanhejny.psp.model.PersistenceRecord;
import com.stanhejny.psp.model.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PaymentAcquirerServiceTest {

    ServerRequest mockRequest = Mockito.mock(ServerRequest.class);
    PersistenceService mockPersistenceService = Mockito.mock(PersistenceService.class);
    PaymentAcquirerClient mockPaymentAcquirerClient = Mockito.mock(PaymentAcquirerClient.class);

    @Test
    void authorizeTransactionApprove() {
        // given
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("374245455400126");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        PersistenceRecord originalRecord = new PersistenceRecord(cardTransaction);
        PersistenceRecord updatedRecord = new PersistenceRecord(cardTransaction);
        updatedRecord.setTransactionStatus(TransactionStatus.APPROVED);
        AcquirerResponse acquirerResponse = AcquirerResponse.builder()
                .timestamp(77665544L)
                .transactionId("1ab2c3")
                .maskedCardNumber("************0126")
                .decision("approved")
                .merchantId("111aaa")
                .build();

        Mockito.when(mockRequest.bodyToMono(CardTransaction.class)).thenReturn(Mono.just(cardTransaction));
        Mockito.when(mockPersistenceService.store(Mockito.any())).thenReturn(Mono.just(originalRecord));
        Mockito.when(mockPersistenceService.updateStatus(Mockito.any(), Mockito.eq(TransactionStatus.APPROVED), Mockito.any()))
                .thenReturn(Mono.just(updatedRecord));
        Mockito.when(mockPaymentAcquirerClient.authorize(Mockito.any())).thenReturn(Mono.just(acquirerResponse));
        PaymentAcquirerService testSubject = new PaymentAcquirerService(mockPersistenceService, mockPaymentAcquirerClient);

        // when
        ServerResponse response = testSubject.authorizeTransaction(mockRequest).block();

        // then
        assertEquals(response.statusCode(), HttpStatus.OK);
        Mockito.verify(mockPersistenceService, Mockito.times(1)).store(Mockito.any());
        Mockito.verify(mockPersistenceService, Mockito.times(1)).updateStatus(Mockito.any(), Mockito.eq(TransactionStatus.APPROVED), Mockito.any());
    }

    @Test
    void authorizeTransactionDeny() {
        // given
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("4263982640269299");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("777");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        PersistenceRecord originalRecord = new PersistenceRecord(cardTransaction);
        PersistenceRecord updatedRecord = new PersistenceRecord(cardTransaction);
        updatedRecord.setTransactionStatus(TransactionStatus.DENIED);
        AcquirerResponse acquirerResponse = AcquirerResponse.builder()
                .timestamp(77665544L)
                .transactionId("1ab2c3")
                .maskedCardNumber("************0126")
                .decision("denied")
                .merchantId("111aaa")
                .build();

        Mockito.when(mockRequest.bodyToMono(CardTransaction.class)).thenReturn(Mono.just(cardTransaction));
        Mockito.when(mockPersistenceService.store(Mockito.any())).thenReturn(Mono.just(originalRecord));
        Mockito.when(mockPersistenceService.updateStatus(Mockito.any(), Mockito.eq(TransactionStatus.DENIED), Mockito.any()))
                .thenReturn(Mono.just(updatedRecord));
        Mockito.when(mockPaymentAcquirerClient.authorize(Mockito.any())).thenReturn(Mono.just(acquirerResponse));
        PaymentAcquirerService testSubject = new PaymentAcquirerService(mockPersistenceService, mockPaymentAcquirerClient);

        // when
        ServerResponse response = testSubject.authorizeTransaction(mockRequest).block();

        // then
        assertEquals(response.statusCode(), HttpStatus.OK);
        Mockito.verify(mockPersistenceService, Mockito.times(1)).store(Mockito.any());
        Mockito.verify(mockPersistenceService, Mockito.times(1)).updateStatus(Mockito.any(), Mockito.eq(TransactionStatus.DENIED), Mockito.any());
    }

    @Test
    void authorizeTransactionWrongCardNumber() {
        // given
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCardNumber("4263982640269999");
        cardTransaction.setExpiryDate("07/27");
        cardTransaction.setCVV("7727");
        cardTransaction.setAmount("777.50");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setMerchantId("1a2b3c");
        PersistenceRecord originalRecord = new PersistenceRecord(cardTransaction);
        PersistenceRecord updatedRecord = new PersistenceRecord(cardTransaction);
        updatedRecord.setTransactionStatus(TransactionStatus.DENIED);
        AcquirerResponse acquirerResponse = AcquirerResponse.builder()
                .timestamp(77665544L)
                .transactionId("1ab2c3")
                .maskedCardNumber("************0126")
                .decision("approved")
                .merchantId("111aaa")
                .build();

        Mockito.when(mockRequest.bodyToMono(CardTransaction.class)).thenReturn(Mono.just(cardTransaction));
        Mockito.when(mockPersistenceService.store(Mockito.any())).thenReturn(Mono.just(originalRecord));
        Mockito.when(mockPersistenceService.updateStatus(Mockito.any(), Mockito.eq(TransactionStatus.DENIED), Mockito.any()))
                .thenReturn(Mono.just(updatedRecord));
        Mockito.when(mockPaymentAcquirerClient.authorize(Mockito.any())).thenReturn(Mono.just(acquirerResponse));

        PaymentAcquirerService testSubject = new PaymentAcquirerService(mockPersistenceService, mockPaymentAcquirerClient);

        // when
        ServerResponse response = testSubject.authorizeTransaction(mockRequest).block();

        // then
        assertEquals(response.statusCode(), HttpStatus.valueOf("UNPROCESSABLE_ENTITY"));
        Mockito.verify(mockPersistenceService, Mockito.times(1)).store(Mockito.any());
        Mockito.verify(mockPersistenceService, Mockito.times(0)).updateStatus(Mockito.any(), Mockito.eq(TransactionStatus.DENIED), Mockito.any());
        Mockito.verify(mockPaymentAcquirerClient, Mockito.times(0)).authorize(Mockito.any());
    }

    @Test
    void listAllTransactions() {
        // given
        CardTransaction cardTransactionAmex = new CardTransaction();
        cardTransactionAmex.setCardNumber("374245455400126");
        cardTransactionAmex.setExpiryDate("07/27");
        cardTransactionAmex.setCVV("7727");
        cardTransactionAmex.setAmount("777.50");
        cardTransactionAmex.setCurrency("EUR");
        cardTransactionAmex.setMerchantId("1a2b3c");
        PersistenceRecord originalRecordAmex = new PersistenceRecord(cardTransactionAmex);

        CardTransaction cardTransactionVisa = new CardTransaction();
        cardTransactionVisa.setCardNumber("4263982640269299");
        cardTransactionVisa.setExpiryDate("07/27");
        cardTransactionVisa.setCVV("777");
        cardTransactionVisa.setAmount("777.50");
        cardTransactionVisa.setCurrency("EUR");
        cardTransactionVisa.setMerchantId("1a2b3c");
        PersistenceRecord originalRecordVisa = new PersistenceRecord(cardTransactionVisa);

        Flux<PersistenceRecord> allTransactions = Flux.fromArray(new PersistenceRecord[]{originalRecordAmex, originalRecordVisa});
        Mockito.when(mockPersistenceService.listAllTransactions()).thenReturn(allTransactions);

        PaymentAcquirerService testSubject = new PaymentAcquirerService(mockPersistenceService, mockPaymentAcquirerClient);

        // when
        ServerResponse response = testSubject.listAllTransactions(mockRequest).block();

        // then
        assertEquals(response.statusCode(), HttpStatus.OK);
        try {
            Method entityMethod = response.getClass().getMethod("entity");
            entityMethod.setAccessible(true);
            Object entity = entityMethod.invoke(response);
            assertEquals(entity.getClass(), ArrayList.class);
            assertEquals(2, ((ArrayList)entity).size());
        } catch (Exception ex) {
            assertEquals(0, 1);
        }
    }
}