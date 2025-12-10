package uk.gov.hmcts.cp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.cp.entities.ExampleEntity;
import uk.gov.hmcts.cp.integration.IntegrationTestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExampleRepositoryTest extends IntegrationTestBase {

    @Autowired
    ExampleRepository exampleRepository;

    @BeforeEach
    void beforeEach() {
        exampleRepository.deleteAll();
    }

    @Test
    @Transactional
    void saveAndQueryShouldReturnEntityByCaseId() {
        ExampleEntity savedEntity = exampleRepository.save(ExampleEntity.builder().exampleText("Some info").build());
        ExampleEntity retrievedEntity = exampleRepository.getReferenceById(savedEntity.getId());
        assertNotNull(retrievedEntity.getId());
        assertThat(retrievedEntity.getExampleText()).isEqualTo("Some info");
    }
}