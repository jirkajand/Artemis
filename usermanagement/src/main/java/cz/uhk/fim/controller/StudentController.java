package cz.uhk.fim.controller;

import cz.uhk.fim.service.StudentService;
import cz.uhk.fim.usermanagement.api.StudentApi;
import cz.uhk.fim.usermanagement.model.ResponseStudentDetail;
import cz.uhk.fim.usermanagement.model.ResponseStudentNavbar;
import cz.uhk.fim.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentService studentService;

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
}
