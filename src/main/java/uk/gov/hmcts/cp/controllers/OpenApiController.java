package uk.gov.hmcts.cp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.cp.openapi.api.CourtHouseApi;
import uk.gov.hmcts.cp.openapi.model.CourtHousesschema;
import uk.gov.hmcts.cp.services.OpenApiService;

@RestController
@RequiredArgsConstructor
public class OpenApiController implements CourtHouseApi {

    private final OpenApiService openApiService;

    @Override
    public ResponseEntity<CourtHousesschema> getCourthouseByCourtId(String courtId) {
        CourtHousesschema courtHousesschema = openApiService.getCourtHouse(courtId);
        return new ResponseEntity<>(courtHousesschema, HttpStatus.OK);
    }

}
