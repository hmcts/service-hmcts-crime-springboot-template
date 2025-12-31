package uk.gov.hmcts.cp.controllers;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.TraceContext;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.model.ErrorResponse;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void handleResponseStatusExceptionShouldReturnErrorResponseWithCorrectFields() {
        // Arrange
        final Tracer tracer = mock(Tracer.class);
        final Span span = mock(Span.class);
        final TraceContext context = mock(TraceContext.class);

        when(tracer.currentSpan()).thenReturn(span);
        when(span.context()).thenReturn(context);
        when(context.traceId()).thenReturn("test-trace-id");

        final GlobalExceptionHandler handler = new GlobalExceptionHandler(tracer);

        final String reason = "Test error";
        final ResponseStatusException exception =
                new ResponseStatusException(HttpStatus.NOT_FOUND, reason);

        final Instant beforeCall = Instant.now();

        // Act
        final ResponseEntity<ErrorResponse> response =
                handler.handleResponseStatusException(exception);

        final Instant afterCall = Instant.now();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        final ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals("404", error.getError());
        assertEquals(reason, error.get
