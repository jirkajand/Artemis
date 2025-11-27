package cz.uhk.fim.settingsservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faculty_entity")
public class FacultyEntity extends BaseEntity {

    private String facultyNameInternational;

    private String facultyNameLocal;

    private String color;

    private String shortName;
}
