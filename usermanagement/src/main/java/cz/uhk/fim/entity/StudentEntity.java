package cz.uhk.fim.entity;

import cz.uhk.fim.entity.enums.AccountStatusEnum;
import cz.uhk.fim.entity.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "students")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public abstract class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "keycloak_id")
    private UUID keycloakId;

    @Column(unique = true, nullable = false)
    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    private AccountStatusEnum accountStatus;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private UUID facultyId;

    private String bio;

    @ElementCollection
    @CollectionTable(
            name = "student_favourite_activities",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "activity")
    @Builder.Default
    private Set<String> favouriteActivities = new HashSet<>();


    private Boolean isActive;


    private Boolean emailMarketingChecked;


    private Boolean termsAndConditionsChecked;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private String profilePicturePath;
}
