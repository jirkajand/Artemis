package cz.uhk.fim.controller;

import cz.uhk.fim.service.InternationalStudentService;
import cz.uhk.fim.usermanagement.api.InternationalStudentApi;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InternationalStudentController implements InternationalStudentApi {

    private final InternationalStudentService internationalStudentService;

    @Override
    public ResponseEntity<RegisterInternationalStudentResponse> registerInternationalStudent(RegisterInternationalStudentRequest registerInternationalStudentRequest) {
        internationalStudentService.registerInternationalStudent(registerInternationalStudentRequest);
        return ResponseEntity.ok(new RegisterInternationalStudentResponse().result("OK"));
    }
}