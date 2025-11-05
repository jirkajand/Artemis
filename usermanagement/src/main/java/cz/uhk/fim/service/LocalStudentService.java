package cz.uhk.fim.service;

import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.mapper.LocalStudentMapper;
import cz.uhk.fim.repository.LocalStudentRepository;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalStudentService {

    private final LocalStudentRepository localStudentRepository;
    private final LocalStudentMapper localStudentMapper;
    private final KeycloakService keycloakService;
    private final ProfilePicturesService profilePicturesService;

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

    public void completeLocalStudentProfile(UUID localStudentId, UUID facultyId, String description, Boolean emailMarketingChecked, MultipartFile profilePicture) {
        var localStudentOpt = getLocalStudentById(localStudentId);
        if (localStudentOpt.isEmpty()) {
            throw new NotFoundException("Student with id " + localStudentId + " not found.");
        }

        var localStudent = localStudentOpt.get();

        localStudent.setBio(description);
        localStudent.setFacultyId(facultyId);
        localStudent.setEmailMarketingChecked(emailMarketingChecked);

        var profilePicturePath = profilePicturesService.storeProfilePicture(localStudentId, profilePicture);
        profilePicturePath.ifPresent(localStudent::setProfilePicturePath);
        //todo handle state if profile picture upload fails what should happen??

        localStudentRepository.save(localStudent);
    }


    public Optional<LocalStudentEntity> getLocalStudentById(UUID localStudentId) {
        return localStudentRepository.findById(localStudentId);
    }
}
