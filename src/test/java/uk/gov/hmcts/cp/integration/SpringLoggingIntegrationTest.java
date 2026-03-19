package uk.gov.hmcts.cp.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.cp.filters.tracing.TracingFilter.CORRELATION_ID_KEY;

@Slf4j
class SpringLoggingIntegrationTest extends IntegrationTestBase {

    private final PrintStream originalStdOut = System.out;

    @AfterEach
    void afterEach() {
        System.setOut(originalStdOut);
    }

    UUID correlationId = UUID.randomUUID();

    @Test
    void springboot_test_should_log_correct_fields() throws IOException {
        MDC.put(CORRELATION_ID_KEY, correlationId.toString());
        final ByteArrayOutputStream capturedStdOut = captureStdOut();
        log.info("spring boot test message", new RuntimeException("TestException"));

        final String logMessage = capturedStdOut.toString(StandardCharsets.UTF_8);
        assertThat(logMessage).isNotEmpty();

        final Map<String, Object> capturedFields =
                new ObjectMapper().readValue(logMessage, new TypeReference<>() {
                });

        assertThat(capturedFields.get(CORRELATION_ID_KEY)).isEqualTo(correlationId.toString());
        assertThat(capturedFields.get("timestamp")).isNotNull();
        assertThat(capturedFields.get("message").toString()).contains("spring boot test message");
        assertThat(capturedFields.get("exception").toString())
                .contains("java.lang.RuntimeException: TestException")
                .contains("cp.integration.SpringLoggingIntegrationTest");
        assertThat(capturedFields.get("logger_name"))
                .isEqualTo("uk.gov.hmcts.cp.integration.SpringLoggingIntegrationTest");
        assertThat(capturedFields.get("thread_name")).isEqualTo("Test worker");
        assertThat(capturedFields.get("level")).isEqualTo("INFO");
    }

    private ByteArrayOutputStream captureStdOut() {
        final ByteArrayOutputStream capturedStdOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedStdOut, true, StandardCharsets.UTF_8));
        return capturedStdOut;
    }
}
