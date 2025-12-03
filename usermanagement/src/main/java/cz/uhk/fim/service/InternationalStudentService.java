package cz.uhk.fim.service;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.entity.StudentEntity;
import cz.uhk.fim.kafka.SendEmailKafkaProducer;
import cz.uhk.fim.mapper.InternationalStudentMapper;
import cz.uhk.fim.mapper.PageableMapper;
import cz.uhk.fim.repository.InternationalStudentRepository;
import cz.uhk.fim.usermanagement.kafka.model.NotificationSendEmailMessage;
import cz.uhk.fim.usermanagement.model.AssignedInternationalStudent;
import cz.uhk.fim.usermanagement.model.GetAllInternationalStudentsAnonymous200Response;
import cz.uhk.fim.usermanagement.model.InternationalStudentProfile;
import cz.uhk.fim.usermanagement.model.InternationalStudentProfileEditRequest;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import cz.uhk.fim.utils.KeycloakUtils;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternationalStudentService {

    private final InternationalStudentRepository internationalStudentRepository;
    private final InternationalStudentMapper internationalStudentMapper;
    private final PageableMapper pageableMapper;
    private final KeycloakService keycloakService;
    private final ProfilePicturesService profilePicturesService;
    private final SemesterService semesterService;
    private final SendEmailKafkaProducer sendEmailKafkaProducer;

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
        var currentSemesterId = semesterService.getSemesterIdForRegistration();

        currentSemesterId.ifPresent(uuid -> internationalStudentEntity.setSemesterIds(new ArrayList<>(List.of(uuid))));
        // Possibly extra logic: e.g., validate country, home university, accommodation, etc.

        // Save to DB
        return internationalStudentRepository.save(internationalStudentEntity);
    }

    public void completeInternationalStudentProfile(UUID internationalStudentId, String facultyId, String description, Boolean emailMarketingChecked, MultipartFile profilePicture, String homeUniversity, String accommodation) {
        var internationalStudentOpt = getInternationalStudentById(internationalStudentId);
        if (internationalStudentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "International student not found with id: " + internationalStudentId);
        }

        var internationalStudent = internationalStudentOpt.get();

        internationalStudent.setBio(description);
        try {
            internationalStudent.setFacultyId(UUID.fromString(facultyId));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid facultyId format: " + facultyId);
        }
        internationalStudent.setEmailMarketingChecked(emailMarketingChecked);
        internationalStudent.setHomeUniversity(homeUniversity);
        internationalStudent.setAccommodation(accommodation);

        // Handle profile picture upload
        // Assuming ProfilePicturesService is available and injected
        var profilePicturePath = profilePicturesService.storeProfilePicture(internationalStudentId, profilePicture);
        profilePicturePath.ifPresent(internationalStudent::setProfilePicturePath);

        internationalStudent.setHasSecondaryRegistrationDone(true);

        internationalStudentRepository.save(internationalStudent);
    }

    public Optional<InternationalStudentEntity> getInternationalStudentById(UUID internationalStudentId) {
        return internationalStudentRepository.findById(internationalStudentId);
    }

    public InternationalStudentProfile getInternationalStudentProfile(UUID internationalStudentId) {
        var internationalStudentOpt = getInternationalStudentById(internationalStudentId);
        if (internationalStudentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "International student not found with id: " + internationalStudentId);
        }
        return internationalStudentMapper.toInternationalStudentProfile(internationalStudentOpt.get());
    }


    public InternationalStudentEntity assignLocalStudentToInternationalStudent(UUID internationalStudentId, LocalStudentEntity localStudentEntity) {
        var internationalStudent = getInternationalStudentById(internationalStudentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "International Student with id " + internationalStudentId + " not found."));

        internationalStudent.setAssignedBuddy(localStudentEntity);
        var saved = internationalStudentRepository.save(internationalStudent);

        sendEmailKafkaProducer.sendEmailMessage(NotificationSendEmailMessage.NotificationType.ASSIGNED_BY_LOCAL_STUDENT, localStudentEntity, saved);

        return saved;
    }

    public GetAllInternationalStudentsAnonymous200Response getAllInternationalStudents(Integer page, Integer size, @Nullable UUID semesterId, @Nullable UUID facultyId, @Nullable String countryCode, @Nullable Boolean containAssigned) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var internationalStudents = internationalStudentRepository.findAllByFilters(pageable, semesterId, facultyId, countryCode, containAssigned, true);
        return new GetAllInternationalStudentsAnonymous200Response()
                    .students(internationalStudents.stream()
                            .map(internationalStudentMapper::toInternationalStudentAnonymous)
                            .toList())
                    .pageable(pageableMapper.toResponse(internationalStudents));
    }


    public List<AssignedInternationalStudent> mapToAssignedStudents(Set<InternationalStudentEntity> internationalStudentEntities) {
        return internationalStudentEntities.stream()
                .filter(StudentEntity::getIsActive)
                .map(internationalStudentMapper::toAssignedInternationalStudent)
                .toList();
    }

    public InternationalStudentProfile updateInternationalStudentById(UUID internationalStudentId, InternationalStudentProfileEditRequest internationalStudentProfileEditRequest) {
        var internationalStudent = getInternationalStudentById(internationalStudentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "International student not found with id: " + internationalStudentId));
        var needToUpdateKeycloak = KeycloakUtils.isChangedKeycloakAttribute(internationalStudentProfileEditRequest, internationalStudent);
        internationalStudentMapper.updateInternationalStudentFromEditRequest(internationalStudentProfileEditRequest, internationalStudent);
        internationalStudentRepository.save(internationalStudent);
        if (needToUpdateKeycloak) {
            keycloakService.updateKeycloakUserAttributes(internationalStudent,
                    internationalStudentProfileEditRequest.getEmail(), internationalStudentProfileEditRequest.getFirstName(), internationalStudentProfileEditRequest.getLastName());
        }
        return internationalStudentMapper.toInternationalStudentProfile(internationalStudent);
    }


    public boolean isInternationalStudentPickedByStudent(UUID internationalStudentId, UUID localStudentId) {
        var internationalStudentOpt = getInternationalStudentById(internationalStudentId);
        if (internationalStudentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "International student not found with id: " + internationalStudentId);
        }
        return internationalStudentOpt.get().getAssignedBuddy() != null &&
                internationalStudentOpt.get().getAssignedBuddy().getId().equals(localStudentId);
    }

    public InternationalStudentEntity save(InternationalStudentEntity internationalStudentEntity) {
        return internationalStudentRepository.save(internationalStudentEntity);
    }


    public void deleteInternationalStudentById(UUID internationalStudentId) {
        var internationalStudent = getInternationalStudentById(internationalStudentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "International student not found with id: " + internationalStudentId)
        );
        if (Objects.nonNull(internationalStudent.getKeycloakId())) {
            keycloakService.deleteUser(internationalStudent.getKeycloakId());
        }
        internationalStudentRepository.delete(internationalStudent);
    }
}
