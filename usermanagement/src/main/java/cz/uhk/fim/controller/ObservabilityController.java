package cz.uhk.fim.controller;

import cz.uhk.fim.usermanagement.api.HealthApi;
import cz.uhk.fim.usermanagement.model.HealthCheckResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ObservabilityController implements HealthApi {

    @Override
    public ResponseEntity<HealthCheckResponse> getHealthCheck() {
        log.info("getHealthCheck");
        return ResponseEntity.ok(
                new HealthCheckResponse()
                        .status("OK")
        );
    }
}
