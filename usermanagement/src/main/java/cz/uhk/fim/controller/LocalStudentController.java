package cz.uhk.fim.controller;

import cz.uhk.fim.service.LocalStudentService;
import cz.uhk.fim.service.StudentService;
import cz.uhk.fim.usermanagement.api.LocalStudentApi;
import cz.uhk.fim.usermanagement.model.AssignInternationalStudentToLocalStudent200Response;
import cz.uhk.fim.usermanagement.model.GetAssignedInternationalStudentsForLocalStudent200Response;
import cz.uhk.fim.usermanagement.model.LocalStudentProfile;
import cz.uhk.fim.usermanagement.model.LocalStudentProfileDetailsResponse;
import cz.uhk.fim.usermanagement.model.LocalStudentProfileEditRequest;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentResponse;
import cz.uhk.fim.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final StudentService studentService;

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

    @Override
    public ResponseEntity<AssignInternationalStudentToLocalStudent200Response> assignInternationalStudentToLocalStudent(UUID internationalStudentId) {
        localStudentService.assignInternationalStudentToLocalStudent(JwtUtils.getIdFromSecurityContext(SecurityContextHolder.getContext()), internationalStudentId);
        return ResponseEntity.ok(new AssignInternationalStudentToLocalStudent200Response().result("OK"));
    }

    @Override
    public ResponseEntity<GetAssignedInternationalStudentsForLocalStudent200Response> getAssignedInternationalStudentsForLocalStudent() {
        return ResponseEntity.ok(new GetAssignedInternationalStudentsForLocalStudent200Response()
                .students(localStudentService.getAssignedInternationalStudentsForLocalStudent(JwtUtils.getIdFromSecurityContext(SecurityContextHolder.getContext()))));
    }

    @Override
    public ResponseEntity<LocalStudentProfile> getLocalStudentById(UUID localStudentId) {
        var loggedUserKeycloakId = JwtUtils.getIdFromSecurityContext(SecurityContextHolder.getContext());
        var loggedInUserId = studentService.getStudentIdByKeycloakId(loggedUserKeycloakId);
        if (loggedInUserId.equals(localStudentId) || localStudentService.isLocalStudentBuddyOfInternationalStudent(localStudentId, loggedInUserId)) {
            return ResponseEntity.ok(localStudentService.getLocalStudentProfile(localStudentId));
        }
        return ResponseEntity.status(403).build();
    }

    @Override
    public ResponseEntity<LocalStudentProfile> updateLocalStudentById(UUID localStudentId, LocalStudentProfileEditRequest localStudentProfileEditRequest) {
        var loggedUserKeycloakId = JwtUtils.getIdFromSecurityContext(SecurityContextHolder.getContext());
        var loggedInUserId = studentService.getStudentIdByKeycloakId(loggedUserKeycloakId);
        //There could be added validation if logged user is admin then can also update another user's profile
        if (loggedInUserId.equals(localStudentId)) {
            return ResponseEntity.ok(localStudentService.updateLocalStudentById(localStudentId, localStudentProfileEditRequest));
        }
        return ResponseEntity.status(403).build();
    }
}
