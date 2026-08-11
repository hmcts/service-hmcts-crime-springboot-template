package uk.gov.hmcts.cp.acceptance;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Boots the Spring application context for Cucumber acceptance scenarios.
 *
 * <p>Uses the same {@code @SpringBootTest} + {@code test} profile setup as the integration/sanity
 * tests so acceptance and sanity boot identically. All Cucumber code (this glue, the step
 * definitions) lives in this {@code acceptance} package; discovery + glue are configured in
 * {@code src/test/resources/junit-platform.properties}.
 */
@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
}
