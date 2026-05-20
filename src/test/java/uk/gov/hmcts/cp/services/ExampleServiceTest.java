package uk.gov.hmcts.cp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.cp.openapi.model.ExampleResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExampleServiceTest {

    @InjectMocks
    ExampleService exampleService;

    @Test
    void service_should_return_response_for_given_id() {
        ExampleResponse response = exampleService.getExampleById(123L);
        assertThat(response.getExampleId()).isEqualTo(123L);
        assertThat(response.getExampleText()).isNotBlank();
    }
}
