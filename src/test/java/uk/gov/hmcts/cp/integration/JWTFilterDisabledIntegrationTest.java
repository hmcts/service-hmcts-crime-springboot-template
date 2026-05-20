package uk.gov.hmcts.cp.integration;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JWTFilterDisabledIntegrationTest extends IntegrationTestBase {

    @Test
    void filter_should_not_failed_when_disabled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/example/1"))
                .andExpect(status().isOk());
    }
}
