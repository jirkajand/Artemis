package cz.uhk.fim.settingsservice.entity;


import cz.uhk.fim.settingsservice.entity.enums.SemesterType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "semester_entity")
public class SemesterEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private SemesterType semesterType;

    private String semesterName;

    private String year; //2025/2026

    private LocalDate semesterRegisterOpenDate;

}
