package cz.uhk.fim.service;

import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.mapper.LocalStudentMapper;
import cz.uhk.fim.repository.LocalStudentRepository;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalStudentService {

    private final LocalStudentRepository localStudentRepository;
    private final LocalStudentMapper localStudentMapper;
    private final KeycloakService keycloakService;

    @Value("${local-student.default-role}")
    private String defaultRole;

    public LocalStudentEntity registerLocalStudent(RegisterLocalStudentRequest registerLocalStudentRequest) {
        var keycloakId = keycloakService.registerUser(localStudentMapper.toRegisterKeycloakUserDTO(registerLocalStudentRequest), defaultRole);
        if (keycloakId.isEmpty()) {
            log.error("Failed to register user in Keycloak authentication system: Keycloak id is empty.");
            throw new IllegalArgumentException("Failed to register user in Keycloak authentication system: Keycloak id is empty.");
        }
        var localStudentEntity = localStudentMapper.toLocalStudentEntity(registerLocalStudentRequest);
        localStudentEntity.setKeycloakId(UUID.fromString(keycloakId.get()));
        return localStudentRepository.save(localStudentEntity);
    }
}
