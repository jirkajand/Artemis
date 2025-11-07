package cz.uhk.fim.controller;

import cz.uhk.fim.service.LocalStudentService;
import cz.uhk.fim.usermanagement.api.LocalStudentApi;
import cz.uhk.fim.usermanagement.model.LocalStudentProfileDetailsResponse;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocalStudentController implements LocalStudentApi {

    private final LocalStudentService localStudentService;

    @Override
    public ResponseEntity<RegisterLocalStudentResponse> registerLocalStudent(RegisterLocalStudentRequest registerLocalStudentRequest) {
        localStudentService.registerLocalStudent(registerLocalStudentRequest);
        return ResponseEntity.ok(new RegisterLocalStudentResponse().result("OK"));
    }

    @Override
    public ResponseEntity<LocalStudentProfileDetailsResponse> completeLocalStudentProfile(UUID localStudentId, String facultyId, String description, Boolean emailMarketingChecked, MultipartFile profilePicture) {
        localStudentService.completeLocalStudentProfile(localStudentId, UUID.fromString(facultyId), description, emailMarketingChecked, profilePicture);
        return ResponseEntity.ok(new LocalStudentProfileDetailsResponse().result("OK"));
    }
}
