package cz.uhk.fim.service;

import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.mapper.LocalStudentMapper;
import cz.uhk.fim.repository.LocalStudentRepository;
import cz.uhk.fim.usermanagement.model.AssignedInternationalStudent;
import cz.uhk.fim.usermanagement.model.LocalStudentProfile;
import cz.uhk.fim.usermanagement.model.LocalStudentProfileEditRequest;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import cz.uhk.fim.utils.KeycloakUtils;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    @Value("${local-student.default-assigned-students-capacity}")
    private Integer defaultAssignedStudentsCapacity;

    public LocalStudentEntity registerLocalStudent(RegisterLocalStudentRequest registerLocalStudentRequest) {
        var keycloakId = keycloakService.registerUser(localStudentMapper.toRegisterKeycloakUserDTO(registerLocalStudentRequest), defaultRole);
        if (keycloakId.isEmpty()) {
            log.error("Failed to register user in Keycloak authentication system: Keycloak id is empty.");
            throw new IllegalArgumentException("Failed to register user in Keycloak authentication system: Keycloak id is empty.");
        }
        var localStudentEntity = localStudentMapper.toLocalStudentEntity(registerLocalStudentRequest);
        localStudentEntity.setKeycloakId(UUID.fromString(keycloakId.get()));
        localStudentEntity.setAssignedStudentsCapacity(defaultAssignedStudentsCapacity);
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

        localStudent.setHasSecondaryRegistrationDone(true);

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

    public List<AssignedInternationalStudent> getAssignedInternationalStudentsForLocalStudent(UUID keycloakId) {
        var localStudent = getLocalStudentByKeycloakId(keycloakId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local Student with keycloakId " + keycloakId + " not found."));
        if (localStudent.getAssignedStudents().isEmpty()) {
            return List.of();
        }
        return internationalStudentService.mapToAssignedStudents(localStudent.getAssignedStudents());
    }

    public boolean isLocalStudentBuddyOfInternationalStudent(UUID localStudentId, UUID loggedInUserId) {
        var localStudent = getLocalStudentById(localStudentId);
        if (localStudent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Local Student with id " + localStudentId + " not found.");
        }
        return localStudent.get().getAssignedStudents().stream()
                .anyMatch(internationalStudent -> internationalStudent.getId().equals(loggedInUserId));
    }

    public LocalStudentProfile getLocalStudentProfile(UUID localStudentId) {
        var localStudent = getLocalStudentById(localStudentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local Student with id " + localStudentId + " not found."));
        var response = localStudentMapper.toLocalStudentProfile(localStudent);
        response.setAssignedInternationalStudents(internationalStudentService.mapToAssignedStudents(localStudent.getAssignedStudents()));
        return response;
    }

    public LocalStudentProfile updateLocalStudentById(UUID localStudentId, LocalStudentProfileEditRequest localStudentProfileEditRequest) {
        var localStudent = getLocalStudentById(localStudentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local student not found with id: " + localStudentId));
        var needToUpdateKeycloak = KeycloakUtils.isChangedKeycloakAttribute(localStudentProfileEditRequest, localStudent);
        localStudentMapper.updateLocalStudentFromEditRequest(localStudentProfileEditRequest, localStudent);
        localStudentRepository.save(localStudent);
        if (needToUpdateKeycloak) {
            keycloakService.updateKeycloakUserAttributes(localStudent,
                    localStudentProfileEditRequest.getEmail(), localStudentProfileEditRequest.getFirstName(), localStudentProfileEditRequest.getLastName());
        }
        return localStudentMapper.toLocalStudentProfile(localStudent);
    }
}
