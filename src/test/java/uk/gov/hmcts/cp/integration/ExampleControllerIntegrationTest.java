package uk.gov.hmcts.cp.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExampleControllerIntegrationTest extends IntegrationTestBase {

    @Test
    void endpoint_should_return_ok() throws Exception {
        mockMvc.perform(get("/example/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
