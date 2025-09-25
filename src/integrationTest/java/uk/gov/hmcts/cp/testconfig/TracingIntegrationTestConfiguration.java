package uk.gov.hmcts.cp.testconfig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.test.simple.SimpleTracer;
import io.micrometer.tracing.handler.DefaultTracingObservationHandler;
import io.micrometer.observation.ObservationRegistry;

@TestConfiguration
public class TracingIntegrationTestConfiguration {
    
    @Bean
    @Primary
    public Tracer testTracer() {
        return new SimpleTracer();
    }
    
    @Bean
    @Primary
    public ObservationRegistry testObservationRegistry(Tracer tracer) {
        ObservationRegistry registry = ObservationRegistry.create();
        registry.observationConfig().observationHandler(
            new DefaultTracingObservationHandler(tracer)
        );
        return registry;
    }
}
