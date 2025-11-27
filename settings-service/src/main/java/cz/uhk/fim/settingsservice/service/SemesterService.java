package cz.uhk.fim.settingsservice.service;

import cz.uhk.fim.settingsservice.entity.SemesterEntity;
import cz.uhk.fim.settingsservice.mapper.SemesterMapper;
import cz.uhk.fim.settingsservice.model.SemesterCreateRequest;
import cz.uhk.fim.settingsservice.model.SemesterResponse;
import cz.uhk.fim.settingsservice.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;


    public SemesterResponse createSemester(SemesterCreateRequest semesterCreateRequest) {
        var semesterEntity = semesterMapper.toEntity(semesterCreateRequest);
        semesterEntity.setId(null);
        var savedEntity = semesterRepository.save(semesterEntity);
        return semesterMapper.toResponse(savedEntity);
    }

    public Optional<SemesterEntity> createSemester(SemesterEntity semesterEntity) {
        semesterEntity.setId(null);
        return Optional.of(semesterRepository.save(semesterEntity));
    }

    public List<SemesterResponse> getAllSemesters() {
        var semesterEntities = semesterRepository.findAll();
        return semesterEntities.stream()
                .map(semesterMapper::toResponse)
                .toList();
    }

    public SemesterResponse getSemesterById(UUID id) {
        var semesterEntity = semesterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semester not found id: " + id));
        return semesterMapper.toResponse(semesterEntity);
    }

    public SemesterResponse updateSemester(UUID id, SemesterCreateRequest semesterCreateRequest) {
        var existingSemesterEntity = semesterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semester not found id: " + id));

        var updatedSemesterEntity = semesterMapper.toEntity(semesterCreateRequest);
        updatedSemesterEntity.setId(existingSemesterEntity.getId());

        var savedEntity = semesterRepository.save(updatedSemesterEntity);
        return semesterMapper.toResponse(savedEntity);
    }

    public SemesterResponse getCurrentSemester() {
        var semester = semesterRepository.findFirstBySemesterRegisterOpenDateNotNullAndSemesterRegisterOpenDateBeforeOrderBySemesterRegisterOpenDateDesc(LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Current semester not found"));
        return semesterMapper.toResponse(semester);
    }


    public boolean isAnySemesterCreatedToday() {
        LocalDate today = LocalDate.now();
        return semesterRepository.findFirstByCreatedAt_YearAndCreatedAt_MonthAndCreatedAt_DayOfMonth(
                today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth()).isPresent();
    }
}
