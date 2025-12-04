package uk.gov.hmcts.cp.services;

class ExampleServiceTest {
//
//    private final ExampleOldRepository exampleOldRepository = new ExampleInMemoryStubOldRepositoryImpl();
//    private final ExampleService exampleService = new ExampleService(exampleOldRepository);
//
//    @Test
//    void shouldReturnStubbedCourtScheduleResponse_whenValidCaseUrnProvided() {
//        // Arrange
//        final String validCaseUrn = "123-ABC-456";
//
//        // Act
//        final CourtScheduleResponse response = exampleService.getCourtScheduleByCaseUrn(validCaseUrn);
//
//        // Assert
//        assertThat(response).isNotNull();
//        assertThat(response.getCourtSchedule()).isNotEmpty();
//        assertThat(response.getCourtSchedule().get(0).getHearings()).isNotEmpty();
//        assertThat(response.getCourtSchedule().get(0).getHearings().get(0).getCourtSittings()).isNotEmpty();
//        assertThat(response.getCourtSchedule().get(0).getHearings().get(0).getHearingDescription())
//                .isEqualTo("Sentencing for theft case");
//    }
//
//    @Test
//    void shouldThrowBadRequestException_whenCaseUrnIsNull() {
//        // Arrange
//        final String nullCaseUrn = null;
//
//        // Act & Assert
//        assertThatThrownBy(() -> exampleService.getCourtScheduleByCaseUrn(nullCaseUrn))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("400 BAD_REQUEST")
//                .hasMessageContaining("caseUrn is required");
//    }
//
//    @Test
//    void shouldThrowBadRequestException_whenCaseUrnIsEmpty() {
//        // Arrange
//        final String emptyCaseUrn = "";
//
//        // Act & Assert
//        assertThatThrownBy(() -> exampleService.getCourtScheduleByCaseUrn(emptyCaseUrn))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("400 BAD_REQUEST")
//                .hasMessageContaining("caseUrn is required");
//    }
}