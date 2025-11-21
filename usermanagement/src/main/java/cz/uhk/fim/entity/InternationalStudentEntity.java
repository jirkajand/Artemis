package cz.uhk.fim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "international_students")
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternationalStudentEntity extends StudentEntity {

    private String homeUniversity;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Country ISO must be 2 uppercase letters")
    @Column(name = "country_iso", length = 2)
    private String countryISO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_buddy_id")
    private LocalStudentEntity assignedBuddy;

    private String accommodation;

    private UUID semesterId;

}
