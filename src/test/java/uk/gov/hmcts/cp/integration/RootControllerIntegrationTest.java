package uk.gov.hmcts.cp.integration;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.cp.NonTracingIntegrationTestSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Slf4j
class RootControllerIntegrationTest extends NonTracingIntegrationTestSetup {
    @Resource
    private MockMvc mockMvc;

    @Test
    void root_endpoint_should_be_ok() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}