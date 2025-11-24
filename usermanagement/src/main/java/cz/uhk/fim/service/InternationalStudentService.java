package cz.uhk.fim.service;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.mapper.InternationalStudentMapper;
import cz.uhk.fim.mapper.PageableMapper;
import cz.uhk.fim.repository.InternationalStudentRepository;
import cz.uhk.fim.usermanagement.model.GetAllInternationalStudentsAnonymous200Response;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
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

        internationalStudentRepository.save(internationalStudent);
    }

    public Optional<InternationalStudentEntity> getInternationalStudentById(UUID internationalStudentId) {
        return internationalStudentRepository.findById(internationalStudentId);
    }


    public InternationalStudentEntity assignLocalStudentToInternationalStudent(UUID internationalStudentId, LocalStudentEntity localStudentEntity) {
        var internationalStudent = getInternationalStudentById(internationalStudentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "International Student with id " + internationalStudentId + " not found."));

        internationalStudent.setAssignedBuddy(localStudentEntity);
        return internationalStudentRepository.save(internationalStudent);
    }

    public GetAllInternationalStudentsAnonymous200Response getAllInternationalStudents(Integer page, Integer size, @Nullable UUID semesterId, @Nullable UUID facultyId, @Nullable String countryCode) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var internationalStudents = internationalStudentRepository.findAllByFilters(pageable, semesterId, facultyId, countryCode);
        return new GetAllInternationalStudentsAnonymous200Response()
                    .students(internationalStudents.stream()
                            .map(internationalStudentMapper::toInternationalStudentAnonymous)
                            .toList())
                    .pageable(pageableMapper.toResponse(internationalStudents));
    }
}
