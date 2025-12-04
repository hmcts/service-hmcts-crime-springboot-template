package uk.gov.hmcts.cp.testconfig;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.micrometer.tracing.opentelemetry.autoconfigure.OpenTelemetryTracingAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@EnableAutoConfiguration(exclude = {
        OpenTelemetryTracingAutoConfiguration.class
})
public class NonTracingIntegrationTestConfiguration {
}
