package cz.uhk.fim.settingsservice.service;

import cz.uhk.fim.settingsservice.entity.SemesterEntity;
import cz.uhk.fim.settingsservice.events.PushSemesterToKafka;
import cz.uhk.fim.settingsservice.kafka.model.SemesterMessage;
import cz.uhk.fim.settingsservice.mapper.SemesterMapper;
import cz.uhk.fim.settingsservice.model.SemesterCreateRequest;
import cz.uhk.fim.settingsservice.model.SemesterResponse;
import cz.uhk.fim.settingsservice.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    private final ApplicationEventPublisher eventPublisher;


    public SemesterResponse createSemester(SemesterCreateRequest semesterCreateRequest) {
        var semesterEntity = semesterMapper.toEntity(semesterCreateRequest);
        semesterEntity.setId(null);
        var savedEntity = semesterRepository.save(semesterEntity);
        return semesterMapper.toResponse(savedEntity);
    }

    public Optional<SemesterEntity> createSemester(SemesterEntity semesterEntity) {
        semesterEntity.setId(null);
        var saved = Optional.of(semesterRepository.save(semesterEntity));

        eventPublisher.publishEvent(new PushSemesterToKafka(saved.get().getId()));

        return saved;
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

        eventPublisher.publishEvent(new PushSemesterToKafka(savedEntity.getId()));

        return semesterMapper.toResponse(savedEntity);
    }

    public SemesterResponse getCurrentSemester() {
        var semester = semesterRepository.findFirstBySemesterRegisterOpenDateNotNullAndSemesterRegisterOpenDateBeforeOrderBySemesterRegisterOpenDateDesc(LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Current semester not found"));
        return semesterMapper.toResponse(semester);
    }


    public boolean isAnySemesterCreatedToday() {
        ZoneId zone = ZoneId.systemDefault();
        OffsetDateTime start = LocalDate.now(zone).atStartOfDay(zone).toOffsetDateTime();
        OffsetDateTime end = start.plusDays(1);
        return semesterRepository.findFirstByCreatedAtBetweenOrderByCreatedAtDesc(start, end).isPresent();
    }

    public SemesterMessage getSemesterMessage(SemesterEntity entity) {
        var message = semesterMapper.toMessage(entity);
        message.setDefaultRegistration(getCurrentSemester().getId().equals(entity.getId()));
        return message;
    }

    public Optional<SemesterEntity> getById(UUID uuid) {
        return semesterRepository.findById(uuid);
    }
}
