package cz.uhk.fim.repository;

import cz.uhk.fim.entity.InternationalStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface InternationalStudentRepository extends JpaRepository<InternationalStudentEntity, UUID> {

}
