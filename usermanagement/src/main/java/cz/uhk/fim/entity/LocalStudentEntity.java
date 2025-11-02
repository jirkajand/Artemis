package cz.uhk.fim.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "local_students")
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocalStudentEntity extends StudentEntity {

    private Integer assignedStudentsCapacity;

    @OneToMany(mappedBy = "assignedBuddy")
    private Set<InternationalStudentEntity> assignedStudents = new HashSet<>();
}
