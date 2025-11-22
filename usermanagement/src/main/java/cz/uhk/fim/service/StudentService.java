package cz.uhk.fim.service;

import cz.uhk.fim.mapper.StudentMapper;
import cz.uhk.fim.repository.InternationalStudentRepository;
import cz.uhk.fim.repository.LocalStudentRepository;
import cz.uhk.fim.usermanagement.model.ResponseStudentDetail;
import cz.uhk.fim.usermanagement.model.ResponseStudentNavbar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final InternationalStudentRepository internationalStudentRepository;
    private final LocalStudentRepository localStudentRepository;

    private final StudentMapper studentMapper;

    public ResponseStudentDetail getCurrentStudentDetail(String keycloakId) {
        var keycloakIdUUID = getKeycloakIdUUID(keycloakId);

        var internationalStudentOpt = internationalStudentRepository.findByKeycloakId(keycloakIdUUID);
        if (internationalStudentOpt.isPresent()) {
            log.info("Found international student with keycloakId: {}", keycloakId);
            return studentMapper.fromInternationalStudentEntity(internationalStudentOpt.get());
        }

        var localStudentOpt = localStudentRepository.findByKeycloakId(keycloakIdUUID);
        if (localStudentOpt.isEmpty()) {
            log.error("No student found with keycloakId: {}", keycloakId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No student found with keycloakId: " + keycloakId);
        }

        return studentMapper.fromLocalStudentEntity(localStudentOpt.get());
    }

    public ResponseStudentNavbar getCurrentStudentForNavbar(String keycloakIdString) {
        var keycloakIdUUID = getKeycloakIdUUID(keycloakIdString);

        var internationalStudentOpt = internationalStudentRepository.findByKeycloakId(keycloakIdUUID);
        if (internationalStudentOpt.isPresent()) {
            log.info("Found international student with keycloakId: {}", keycloakIdString);
            return studentMapper.toResponseStudentNavbarFromInternationalStudent(internationalStudentOpt.get());
        }
        var localStudentOpt = localStudentRepository.findByKeycloakId(keycloakIdUUID);
        if (localStudentOpt.isEmpty()) {
            log.error("No student found with keycloakId: {}", keycloakIdString);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No student found with keycloakId: " + keycloakIdString);
        }
        return studentMapper.toResponseStudentNavbarFromLocalStudent(localStudentOpt.get());
    }


    private UUID getKeycloakIdUUID(String keycloakId) {
        UUID keycloakIdUUID;
        try {
            keycloakIdUUID = UUID.fromString(keycloakId);
        } catch (Exception e) {
            log.error("Invalid UUID format for keycloakId: {}", keycloakId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UUID format for keycloakId: " + keycloakId);
        }
        return keycloakIdUUID;
    }
}
