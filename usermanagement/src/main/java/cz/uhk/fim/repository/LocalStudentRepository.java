package cz.uhk.fim.repository;

import cz.uhk.fim.entity.LocalStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocalStudentRepository  extends JpaRepository<LocalStudentEntity, UUID> {
}
