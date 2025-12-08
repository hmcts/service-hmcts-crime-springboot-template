package uk.gov.hmcts.cp.integration;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import uk.gov.hmcts.cp.entities.ExampleEntity;
import uk.gov.hmcts.cp.filters.jwt.JWTService;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.cp.filters.jwt.JWTFilter.JWT_TOKEN_HEADER;

@SpringBootTest(properties = {"jwt.filter.enabled=true"})
class JWTFilterIntegrationTest extends IntegrationTestBase {

    @Resource
    private JWTService jwtService;

    @Transactional
    @Test
    void filter_should_pass_when_good_token() throws Exception {
        ExampleEntity exampleEntity = insertExample("Filter example");
        final String jwtToken = jwtService.createToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/example/{id}", exampleEntity.getId())
                        .header(JWT_TOKEN_HEADER, jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exampleText").value("Filter example"));
    }

    @Test
    void filter_should_fail_early_when_missing_token() {
        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> mockMvc
                        .perform(
                                MockMvcRequestBuilders.get("/")
                        ))
                .withMessageContaining("No jwt token passed");
    }
}