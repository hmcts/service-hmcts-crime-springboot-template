package uk.gov.hmcts.cp.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JWTFilterDisabledIntegrationTest extends IntegrationTestBase {

    @Test
    void filter_should_not_block_with_401_when_disabled() throws Exception {
        mockMvc.perform(get("/path-to-get-404"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}