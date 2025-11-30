package cz.uhk.fim.processor;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.mapper.AnonymizationMapper;
import cz.uhk.fim.service.InternationalStudentService;
import cz.uhk.fim.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnonymizationProcessor {

    private final AnonymizationMapper anonymizationMapper;
    private final KeycloakService keycloakService;

    private final InternationalStudentService internationalStudentService;

    public void anonymizeInternationalStudent(UUID studentId) {
        log.info("Anonymizing international student data for student ID: {}", studentId);
        anonymizeInternationalStudent(internationalStudentService.getInternationalStudentById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "International student with ID " + studentId + " not found.")));
    }

    public void anonymizeInternationalStudent(InternationalStudentEntity internationalStudentEntity) {
        log.info("Anonymizing international student data...");
        keycloakService.deleteUser(internationalStudentEntity.getKeycloakId());

        internationalStudentEntity = anonymizationMapper.anonymizeInternationalStudent(internationalStudentEntity);

        internationalStudentService.save(internationalStudentEntity);
    }

    public void anonymizeInternationalStudentsBulk(@Valid List<UUID> internationalStudentIds) {
        List<String> failures = new java.util.ArrayList<>();
        for (UUID studentId : internationalStudentIds) {
            try {
                anonymizeInternationalStudent(studentId);
            } catch (Exception e) {
                log.error("Failed to anonymize student with ID {}: {}", studentId, e.getMessage());
                failures.add("ID: " + studentId + " - " + e.getMessage());
            }
        }
        if (!failures.isEmpty()) {
            String errorMsg = "Bulk anonymization completed with failures: " + String.join("; ", failures);
            log.warn(errorMsg);
            throw new ResponseStatusException(HttpStatus.MULTI_STATUS, errorMsg);
        }
    }
}
