package cz.uhk.fim.controller;

import cz.uhk.fim.service.ProfilePicturesService;
import cz.uhk.fim.service.StudentService;
import cz.uhk.fim.usermanagement.api.StudentApi;
import cz.uhk.fim.usermanagement.model.ResponseStudentDetail;
import cz.uhk.fim.usermanagement.model.ResponseStudentNavbar;
import cz.uhk.fim.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentService studentService;
    private final ProfilePicturesService profilePicturesService;

    @Override
    public ResponseEntity<ResponseStudentDetail> getCurrentStudentDetail() {
        var keycloakIdString = JwtUtils.getKeycloakIdFromJwt((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok(studentService.getCurrentStudentDetail(keycloakIdString));
    }

    @Override
    public ResponseEntity<ResponseStudentNavbar> getCurrentStudentForNavbar() {
        var keycloakIdString = JwtUtils.getKeycloakIdFromJwt((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok(studentService.getCurrentStudentForNavbar(keycloakIdString));
    }

    @Override
    public ResponseEntity<Resource> getCurrentStudentProfilePicture(UUID studentId) {
        var resource = profilePicturesService.getProfilePictureResource(studentId);
        String contentType = "application/octet-stream";
        String filename = resource.getFilename();
        if (filename != null) {
            int idx = filename.lastIndexOf('.');
            if (idx != -1 && idx < filename.length() - 1) {
                String ext = filename.substring(idx + 1).toLowerCase();
                contentType = switch (ext) {
                    case "png" -> "image/png";
                    case "jpg", "jpeg" -> "image/jpeg";
                    case "gif" -> "image/gif";
                    case "bmp" -> "image/bmp";
                    case "webp" -> "image/webp";
                    default -> "application/octet-stream";
                };
            }
        }
        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(resource);
    }
}
