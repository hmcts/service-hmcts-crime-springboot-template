package uk.gov.hmcts.cp.filters.tracing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.hmcts.cp.filters.tracing.TracingFilter.SPANE_ID;
import static uk.gov.hmcts.cp.filters.tracing.TracingFilter.TRACE_ID;

@ExtendWith(MockitoExtension.class)
class TracingFilterTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    TracingFilter tracingFilter;

    @Test
    void filter_should_use_incoming_traceId() throws ServletException, IOException {
        when(request.getHeader(TRACE_ID)).thenReturn("incoming-traceId");
        when(request.getHeader(SPANE_ID)).thenReturn("incoming-spanId");

        tracingFilter.doFilterInternal(request, response, filterChain);

        verify(response).setHeader(TRACE_ID, "incoming-traceId");
        assertThat(MDC.get(TRACE_ID)).isEqualTo("incoming-traceId");
        verify(response).setHeader(SPANE_ID, "incoming-spanId");
        assertThat(MDC.get(SPANE_ID)).isEqualTo("incoming-spanId");
    }
}