package cz.uhk.fim.entity;

import cz.uhk.fim.entity.enums.SexEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "local_student_entity")
public class LocalStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "keycloak_id")
    private UUID keycloakId;

    private String name;
    private String surname;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    //faculty of study - will be later entity

    private String description;

    private String favouriteActivities;

    private Boolean emailMarketingChecked;

    private Boolean termsAndConditionsChecked;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

}
