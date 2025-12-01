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

        if (semesterRepository.existsById(semesterMessage.getId())) {
            var entity = semesterMapper.toEntity(semesterMessage);
            if (Boolean.TRUE.equals(entity.getDefaultRegistration())) {
                var existingDefault = semesterRepository.findAll().stream()
                        .filter(SemesterEntity::getDefaultRegistration)
                        .findFirst();
                existingDefault.ifPresent(sem -> {
                    sem.setDefaultRegistration(false);
                    semesterRepository.save(sem);
                });
            }
            semesterRepository.save(entity);
            return;
        }
        var entity = semesterMapper.toEntity(semesterMessage);
        semesterRepository.save(entity);
    }

    public Optional<UUID> getSemesterIdForRegistration() {
        var currentSemester = semesterRepository.findAll().stream()
                .filter(SemesterEntity::getDefaultRegistration)
                .findFirst();
        if (currentSemester.isEmpty()) {
            log.error("No current semester found");
            return Optional.empty();
        }
        return Optional.of(currentSemester.get().getId());
    }
}
