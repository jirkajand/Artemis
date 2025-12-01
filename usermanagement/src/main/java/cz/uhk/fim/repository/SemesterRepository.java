package cz.uhk.fim.repository;

import cz.uhk.fim.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SemesterRepository extends JpaRepository<SemesterEntity, UUID> {
}
