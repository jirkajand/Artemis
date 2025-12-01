package cz.uhk.fim.service;

import cz.uhk.fim.entity.SemesterEntity;
import cz.uhk.fim.mapper.SemesterMapper;
import cz.uhk.fim.repository.SemesterRepository;
import cz.uhk.fim.usermanagement.kafka.model.SemesterMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    @Transactional
    public void consumeSemesterMessage(SemesterMessage semesterMessage) {
        log.info("Consuming semester message");

        var existingEntityOpt = semesterRepository.findById(semesterMessage.getId());
        if (existingEntityOpt.isPresent()) {
            var entity = existingEntityOpt.get();
            // Update fields from semesterMessage
            entity.setSemesterName(semesterMessage.getSemesterName());
            entity.setYear(semesterMessage.getYear());
            entity.setDefaultRegistration(semesterMessage.getDefaultRegistration());
            // If defaultRegistration is being set to true, unset it for others
            if (Boolean.TRUE.equals(entity.getDefaultRegistration())) {
                unsetOtherDefaultSemesters(entity);
            }
            semesterRepository.save(entity);
            return;
        }
        var newEntity = semesterMapper.toEntity(semesterMessage);
        // If defaultRegistration is being set to true, unset it for others
        if (Boolean.TRUE.equals(newEntity.getDefaultRegistration())) {
            unsetOtherDefaultSemesters(newEntity);
        }
        semesterRepository.save(newEntity);

    }

    private void unsetOtherDefaultSemesters(SemesterEntity entity) {
        semesterRepository.findAllByDefaultRegistrationTrue().stream()
                .filter(sem -> !sem.getId().equals(entity.getId()))
                .forEach(sem -> {
                    sem.setDefaultRegistration(false);
                    semesterRepository.save(sem);
                });

    }

    public Optional<UUID> getSemesterIdForRegistration() {
        var currentSemester = semesterRepository.findFirstByDefaultRegistrationTrue();
        if (currentSemester.isEmpty()) {
            log.error("No current semester found");
            return Optional.empty();
        }
        return Optional.of(currentSemester.get().getId());
    }
}
