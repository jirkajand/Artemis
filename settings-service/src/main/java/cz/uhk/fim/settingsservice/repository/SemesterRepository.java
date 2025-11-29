package cz.uhk.fim.settingsservice.repository;

import cz.uhk.fim.settingsservice.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SemesterRepository extends JpaRepository<SemesterEntity, UUID> {

    Optional<SemesterEntity> findFirstBySemesterRegisterOpenDateNotNullAndSemesterRegisterOpenDateBeforeOrderBySemesterRegisterOpenDateDesc(LocalDate date);

    Optional<SemesterEntity> findFirstByCreatedAtBetweenOrderByCreatedAtDesc(OffsetDateTime start, OffsetDateTime end);
}
