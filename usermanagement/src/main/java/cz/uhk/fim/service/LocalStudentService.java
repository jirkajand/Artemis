package cz.uhk.fim.service;

import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.mapper.LocalStudentMapper;
import cz.uhk.fim.repository.LocalStudentRepository;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
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
    private final InternationalStudentService internationalStudentService;

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

    public Optional<LocalStudentEntity> getLocalStudentByKeycloakId(UUID keycloakId) {
        return localStudentRepository.findByKeycloakId(keycloakId);
    }

    @Transactional
    public void assignInternationalStudentToLocalStudent(UUID localStudentKeycloakId, UUID internationalStudentId) {
        var localStudent = getLocalStudentByKeycloakId(localStudentKeycloakId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local Student with keycloakId " + localStudentKeycloakId + " not found."));
        if (Objects.isNull(localStudent.getAssignedStudentsCapacity())
                || (Objects.nonNull(localStudent.getAssignedStudents())
                && !localStudent.getAssignedStudents().isEmpty()
                && localStudent.getAssignedStudents().size() >= localStudent.getAssignedStudentsCapacity())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local Student with id " + localStudent.getId() + " has reached the maximum capacity of assigned international students.");
        }
        var internationalStudent = internationalStudentService.getInternationalStudentById(internationalStudentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "International Student with id " + internationalStudentId + " not found."));

        if (localStudent.getAssignedStudents().contains(internationalStudent)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "International Student with id " + internationalStudentId + " is already assigned to Local Student with id " + localStudent.getId() + ".");
        }

        if (Objects.nonNull(internationalStudent.getAssignedBuddy())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "International Student with id " + internationalStudentId + " is already assigned to another Local Student with id " + internationalStudent.getAssignedBuddy().getId() + ".");
        }

        internationalStudentService.assignLocalStudentToInternationalStudent(internationalStudentId, localStudent);

    }
}
