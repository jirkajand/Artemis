package cz.uhk.fim.controller;

import cz.uhk.fim.service.InternationalStudentService;
import cz.uhk.fim.usermanagement.api.InternationalStudentApi;
import cz.uhk.fim.usermanagement.model.GetAllInternationalStudentsAnonymous200Response;
import cz.uhk.fim.usermanagement.model.InternationalStudentProfileDetailsResponse;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentResponse;
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
public class InternationalStudentController implements InternationalStudentApi {

    private final InternationalStudentService internationalStudentService;

    @Override
    public ResponseEntity<RegisterInternationalStudentResponse> registerInternationalStudent(RegisterInternationalStudentRequest registerInternationalStudentRequest) {
        internationalStudentService.registerInternationalStudent(registerInternationalStudentRequest);
        return ResponseEntity.ok(new RegisterInternationalStudentResponse().result("OK"));
    }

    @Override
    public ResponseEntity<InternationalStudentProfileDetailsResponse> completeInternationalStudentProfile(UUID internationalStudentId, String facultyId, String description, Boolean emailMarketingChecked, MultipartFile profilePicture, String homeUniversity, String accommodation) {
        internationalStudentService.completeInternationalStudentProfile(internationalStudentId, facultyId, description, emailMarketingChecked, profilePicture, homeUniversity, accommodation);
        return ResponseEntity.ok(new InternationalStudentProfileDetailsResponse().result("OK"));
    }

    //todo add preAuthorize or Secured annotation
    @Override
    public ResponseEntity<GetAllInternationalStudentsAnonymous200Response> getAllInternationalStudentsAnonymous(Integer page, Integer size, UUID semesterId, UUID facultyId, String countryCode) {
        return ResponseEntity.ok(internationalStudentService.getAllInternationalStudents(page, size, semesterId, facultyId, countryCode));
    }

}