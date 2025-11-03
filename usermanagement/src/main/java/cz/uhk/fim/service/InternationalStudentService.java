package cz.uhk.fim.service;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.mapper.InternationalStudentMapper;
import cz.uhk.fim.repository.InternationalStudentRepository;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternationalStudentService {

    private final InternationalStudentRepository internationalStudentRepository;
    private final InternationalStudentMapper internationalStudentMapper;
    private final KeycloakService keycloakService;

    @Value("${international-student.default-role}")
    private String defaultRole;

    public InternationalStudentEntity registerInternationalStudent(RegisterInternationalStudentRequest request) {
        // Register in Keycloak
        var keycloakId = keycloakService.registerUser(
                internationalStudentMapper.toRegisterKeycloakUserDTO(request),
                defaultRole
        );

        if (keycloakId.isEmpty()) {
            log.error("Failed to register user in Keycloak authentication system: Keycloak id is empty.");
            throw new IllegalArgumentException("Failed to register user in Keycloak authentication system: Keycloak id is empty.");
        }

        // Map request to entity
        var internationalStudentEntity = internationalStudentMapper.toInternationalStudentEntity(request);
        internationalStudentEntity.setKeycloakId(UUID.fromString(keycloakId.get()));

        // Possibly extra logic: e.g., validate country, home university, accommodation, etc.

        // Save to DB
        return internationalStudentRepository.save(internationalStudentEntity);
    }
}
