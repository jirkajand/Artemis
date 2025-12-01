package cz.uhk.fim.service;

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
                var existingDefault = semesterRepository.findAllByDefaultRegistrationTrue().stream()
                        .filter(sem -> !sem.getId().equals(entity.getId()))
                        .findFirst();
                existingDefault.ifPresent(sem -> {
                    sem.setDefaultRegistration(false);
                    semesterRepository.save(sem);
                });
            }
            semesterRepository.save(entity);
        }
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
