package cz.uhk.fim.repository;

import cz.uhk.fim.entity.InternationalStudentEntity;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InternationalStudentRepository extends JpaRepository<InternationalStudentEntity, UUID> {


    @Query("""
            select student from InternationalStudentEntity student where
                                 (:facultyId is null or student.facultyId = :facultyId) and
                                 (:semesterId is null or student.semesterId = :semesterId) and
                                 (:countryCode is null or student.countryISO = :countryCode) and
                                 (:containAssigned is null or (:containAssigned = true and student.assignedBuddy is not null) or (:containAssigned = false and student.assignedBuddy is null))
            """)
    Page<InternationalStudentEntity> findAllByFilters(Pageable pageable, @Nullable UUID semesterId, @Nullable UUID facultyId, @Nullable String countryCode, @Nullable Boolean containAssigned);

    Optional<InternationalStudentEntity> findByKeycloakId(UUID keycloakIdUUID);
}
