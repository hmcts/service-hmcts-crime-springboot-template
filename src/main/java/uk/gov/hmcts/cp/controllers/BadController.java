package uk.gov.hmcts.cp.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BadController {

    public ResponseEntity<String> badController(@NotNull final String example) {
        log.info("badController example for {}", example);
        return ResponseEntity.ok("Bad logging");
    }
}
