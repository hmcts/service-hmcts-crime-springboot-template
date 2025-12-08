package uk.gov.hmcts.cp.integration;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.cp.config.TestContainersInitialise;
import uk.gov.hmcts.cp.entities.ExampleEntity;
import uk.gov.hmcts.cp.repositories.ExampleRepository;

@SpringBootTest
@ContextConfiguration(initializers = TestContainersInitialise.class)
@AutoConfigureMockMvc
@Slf4j
public abstract class IntegrationTestBase {

    @Resource
    protected MockMvc mockMvc;

    @Autowired
    protected ExampleRepository exampleRepository;

    protected ExampleEntity insertExample(String text) {
        ExampleEntity exampleEntity = exampleRepository.save(ExampleEntity.builder().exampleText(text).build());
        log.info("Inserted example:{}", exampleEntity.getId());
        return exampleEntity;
    }
}
