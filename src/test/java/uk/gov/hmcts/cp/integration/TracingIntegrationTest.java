package uk.gov.hmcts.cp.integration;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.cp.filters.tracing.TracingFilter.CORRELATION_ID_KEY;

class TracingIntegrationTest extends IntegrationTestBase {

    private static final String TEST_CORRELATION_ID = "12345678-1234-1234-1234-123456789012";

    @Test
    void incomingRequestShouldReturnOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void subscriptionEndpointWithCorrelationIdShouldEchoHeaderInResponse() throws Exception {
        MvcResult result = mockMvc.perform(get("/")
                        .header(CORRELATION_ID_KEY, TEST_CORRELATION_ID))
                .andReturn();

        String responseCorrelationId = result.getResponse().getHeader(CORRELATION_ID_KEY);
        assertThat(responseCorrelationId).isEqualTo(TEST_CORRELATION_ID);
    }
}
