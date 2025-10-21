package cz.uhk.fim.settingsservice.controller;

import cz.uhk.fim.settingsservice.api.FacultyApi;
import cz.uhk.fim.settingsservice.model.FacultyCreateRequest;
import cz.uhk.fim.settingsservice.model.FacultyResponse;
import cz.uhk.fim.settingsservice.service.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FacultyController implements FacultyApi {

    private final FacultyService facultyService;

    //todo preauthorize methods
    @Override
    public ResponseEntity<FacultyResponse> createFaculty(FacultyCreateRequest facultyCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.createFaculty(facultyCreateRequest));
    }

    @Override
    public ResponseEntity<Void> deleteFaculty(UUID id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<FacultyResponse>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @Override
    public ResponseEntity<FacultyResponse> getFacultyById(UUID id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    @Override
    public ResponseEntity<FacultyResponse> updateFaculty(UUID id, FacultyCreateRequest facultyCreateRequest) {
        return ResponseEntity.ok(facultyService.updateFaculty(id, facultyCreateRequest));
    }
}
