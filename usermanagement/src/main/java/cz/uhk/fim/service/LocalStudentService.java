package cz.uhk.fim.service;

import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.mapper.LocalStudentMapper;
import cz.uhk.fim.repository.LocalStudentRepository;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalStudentService {

    private final LocalStudentRepository localStudentRepository;
    private final LocalStudentMapper localStudentMapper;
    private final KeycloakService keycloakService;

    public LocalStudentEntity registerLocalStudent(RegisterLocalStudentRequest registerLocalStudentRequest) {
        var keycloakId = keycloakService.registerUser(localStudentMapper.toRegisterKeycloakUserDTO(registerLocalStudentRequest));
        if (keycloakId.isEmpty()) {
            log.error("Keycloak id is empty, cannot register local student.");
            throw new IllegalArgumentException("Keycloak id is empty, cannot register local student.");
        }
        var localStudentEntity = localStudentMapper.toLocalStudentEntity(registerLocalStudentRequest);
        localStudentEntity.setKeycloakId(UUID.fromString(keycloakId.get()));
        return localStudentRepository.save(localStudentEntity);
    }
}
