package uk.gov.hmcts.cp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.cp.openapi.model.ExampleResponse;

/**
 * Stub implementation — replace with your own business logic.
 *
 * @see <a href="https://github.com/hmcts/service-hmcts-springboot-demo">Spring Boot Demo</a>
 * for implementation patterns (JPA / Postgres, caching, messaging, etc.).
 */
@Service
@Slf4j
public class ExampleService {

    public ExampleResponse getExampleById(final long exampleId) {
        return ExampleResponse.builder()
                .exampleId(exampleId)
                .exampleText("Replace this stub — see https://github.com/hmcts/service-hmcts-springboot-demo")
                .build();
    }
}
