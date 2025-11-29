package cz.uhk.fim.controller;

import cz.uhk.fim.service.InternationalStudentService;
import cz.uhk.fim.service.StudentService;
import cz.uhk.fim.usermanagement.api.InternationalStudentApi;
import cz.uhk.fim.usermanagement.model.GetAllInternationalStudentsAnonymous200Response;
import cz.uhk.fim.usermanagement.model.InternationalStudentProfile;
import cz.uhk.fim.usermanagement.model.InternationalStudentProfileDetailsResponse;
import cz.uhk.fim.usermanagement.model.InternationalStudentProfileEditRequest;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentResponse;
import cz.uhk.fim.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InternationalStudentController implements InternationalStudentApi {

    private final InternationalStudentService internationalStudentService;
    private final StudentService studentService;

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

    @Override
    public ResponseEntity<GetAllInternationalStudentsAnonymous200Response> getAllInternationalStudentsAnonymous(Integer page, Integer size, UUID semesterId, UUID facultyId, String countryCode, Boolean containAssigned) {
        return ResponseEntity.ok(internationalStudentService.getAllInternationalStudents(page, size, semesterId, facultyId, countryCode, containAssigned));
    }

    @Override
    public ResponseEntity<InternationalStudentProfile> getInternationalStudentById(UUID internationalStudentId) {
        var loggedUserKeycloakId = JwtUtils.getIdFromSecurityContext(SecurityContextHolder.getContext());
        var loggedInUserId = studentService.getStudentIdByKeycloakId(loggedUserKeycloakId);
        if (loggedInUserId.equals(internationalStudentId) || internationalStudentService.isInternationalStudentPickedByStudent(internationalStudentId, loggedInUserId)) {
            return ResponseEntity.ok(internationalStudentService.getInternationalStudentProfile(internationalStudentId));
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. You can only view your own profile or if you are assigned as a buddy.");
    }

    @Override
    public ResponseEntity<InternationalStudentProfile> updateInternationalStudentById(UUID internationalStudentId, InternationalStudentProfileEditRequest internationalStudentProfileEditRequest) {
        var loggedUserKeycloakId = JwtUtils.getIdFromSecurityContext(SecurityContextHolder.getContext());
        var loggedInUserId = studentService.getStudentIdByKeycloakId(loggedUserKeycloakId);
        //There could be added validation if logged user is admin then can also update another user's profile
        if (loggedInUserId.equals(internationalStudentId)) {
            return ResponseEntity.ok(internationalStudentService.updateInternationalStudentById(internationalStudentId, internationalStudentProfileEditRequest));
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. You can only update your own profile.");
    }
}