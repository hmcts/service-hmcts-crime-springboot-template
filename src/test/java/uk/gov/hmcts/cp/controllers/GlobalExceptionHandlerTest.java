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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        
        final ResponseEntity<ErrorResponse> response =
                handler.handleResponseStatusException(exception);

        final Instant afterCall = Instant.now();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        final ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals("404", error.getError());
        assertEquals(reason, error.getMessage());

        assertNotNull(error.getTimestamp());
        assertTrue(
                !error.getTimestamp().isBefore(beforeCall)
                        && !error.getTimestamp().isAfter(afterCall),
                "Timestamp should be within method execution time"
        );

        assertEquals("test-trace-id", error.getTraceId());
    }
}
