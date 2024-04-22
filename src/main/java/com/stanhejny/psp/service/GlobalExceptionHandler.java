package com.stanhejny.psp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * Handles the exceptions that occur during the chain of request processing.
 * Setting @Order value allows other handlers to handle the exception before it falls through here.
 */
@RestControllerAdvice
@Order(-2)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Record the exception to log file and return HTTP 500 error, with generic message.
     *
     * @param ex the exception being handled
     * @return text to include into the body of the HTTP response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<String> handleException(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        return Mono.just("An error occurred. Please try again later.");
    }
}