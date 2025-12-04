package uk.gov.hmcts.cp.integration;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.cp.NonTracingIntegrationTestSetup;
import uk.gov.hmcts.cp.config.TestContainersInitialise;
import uk.gov.hmcts.cp.entities.ExampleEntity;
import uk.gov.hmcts.cp.repositories.ExampleRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(TestContainersInitialise.class)
@ContextConfiguration(initializers = TestContainersInitialise.class)
class ExampleControllerIntegrationTest extends NonTracingIntegrationTestSetup {
    @Autowired
    ExampleRepository exampleRepository;

    @Resource
    private MockMvc mockMvc;

    @Test
    @SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
    void shouldReturnOkWhenValidUrnIsProvided() throws Exception {
        ExampleEntity exampleEntity = insertExample("Some random text");

        mockMvc.perform(get("/example/{example_id}", exampleEntity.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private ExampleEntity insertExample(String text) {
        return exampleRepository.save(ExampleEntity.builder().exampleText(text).build());
    }
}