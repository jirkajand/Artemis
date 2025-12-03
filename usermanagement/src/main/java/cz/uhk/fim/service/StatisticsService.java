package cz.uhk.fim.service;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.repository.InternationalStudentRepository;
import cz.uhk.fim.repository.LocalStudentRepository;
import cz.uhk.fim.usermanagement.model.UserManagementStatisticsInternationalStudentBySemester;
import cz.uhk.fim.usermanagement.model.UserManagementStatisticsLocalStudentActive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    //There are repositories just to collect data, no updating/creating/deleting. That is a reason why services are not used here.
    private final LocalStudentRepository localStudentRepository;
    private final InternationalStudentRepository internationalStudentRepository;


    public UserManagementStatisticsInternationalStudentBySemester getUserManagementStatisticsInternationalStudentBySemester(UUID semesterId) {
        var internationalStudentsBySemester = internationalStudentRepository.findAllBySemesterIdsContains(semesterId);

        if (internationalStudentsBySemester == null || internationalStudentsBySemester.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No international students found for semester with id " + semesterId + ".");
        }
        var result = new UserManagementStatisticsInternationalStudentBySemester();
        result.setTotalInternationalStudents(internationalStudentsBySemester.size());
        result.setTotalAssignedInternationalStudents(internationalStudentsBySemester.stream()
                .filter(student -> student.getAssignedBuddy() != null || Boolean.TRUE.equals(student.getAnonymizedHasBuddy()))
                .toList()
                .size());
        result.setTotalUnassignedInternationalStudents(internationalStudentsBySemester.stream()
                .filter(student -> student.getAssignedBuddy() == null || Boolean.FALSE.equals(student.getAnonymizedHasBuddy()))
                .toList()
                .size());
        result.setTotalInternationalStudentsCountries(internationalStudentsBySemester.stream()
                .map(InternationalStudentEntity::getCountryISO)
                .distinct()
                .toList()
                .size());
        result.setTotalInternationalStudentsByCountryCode(internationalStudentsBySemester.stream()
                .collect(Collectors.groupingBy(
                        student -> Objects.toString(student.getCountryISO(), ""),
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )
                )));
        result.setTotalInternationalStudentsByFacultyId(internationalStudentsBySemester.stream()
                .collect(Collectors.groupingBy(
                        internationalStudentEntity -> Objects.nonNull(internationalStudentEntity.getFacultyId())
                                ? internationalStudentEntity.getFacultyId().toString()
                                : "",
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )
                )));
        result.setTotalInternationalStudentsByAge(internationalStudentsBySemester.stream()
                .collect(Collectors.groupingBy(
                        student ->
                                Objects.nonNull(student.getDateOfBirth())
                                        ? String.valueOf(Period.between(student.getDateOfBirth(), LocalDate.now()).getYears())
                                        : "",
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )
                )));
        return result;
    }

    public UserManagementStatisticsLocalStudentActive getUserManagementStatisticsLocalStudentActive() {
        return new UserManagementStatisticsLocalStudentActive().totalActiveLocalStudents(localStudentRepository.countByIsActiveTrue());
    }
}
