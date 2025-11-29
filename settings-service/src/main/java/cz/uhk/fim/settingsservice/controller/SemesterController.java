package cz.uhk.fim.settingsservice.controller;

import cz.uhk.fim.settingsservice.api.SemesterApi;
import cz.uhk.fim.settingsservice.model.SemesterCreateRequest;
import cz.uhk.fim.settingsservice.model.SemesterResponse;
import cz.uhk.fim.settingsservice.service.SemesterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SemesterController implements SemesterApi {

    private final SemesterService semesterService;

    @Override
    public ResponseEntity<SemesterResponse> createSemester(SemesterCreateRequest semesterCreateRequest) {
        return ResponseEntity.status(201).body(semesterService.createSemester(semesterCreateRequest));
    }

    @Override
    public ResponseEntity<List<SemesterResponse>> getAllSemesters() {
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

    @Override
    public ResponseEntity<SemesterResponse> getSemesterById(UUID id) {
        return ResponseEntity.ok(semesterService.getSemesterById(id));
    }

    @Override
    public ResponseEntity<SemesterResponse> updateSemester(UUID id, SemesterCreateRequest semesterCreateRequest) {
        return ResponseEntity.ok(semesterService.updateSemester(id, semesterCreateRequest));
    }

    @Override
    public ResponseEntity<SemesterResponse> getCurrentSemester() {
        return ResponseEntity.ok(semesterService.getCurrentSemester());
    }
}
