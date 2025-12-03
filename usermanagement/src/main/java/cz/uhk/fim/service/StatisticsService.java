package cz.uhk.fim.service;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

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
        // Single pass aggregations
        int totalAssigned = 0;
        int totalUnassigned = 0;
        var countrySet = new HashSet<String>();
        var countryCountMap = new HashMap<String, Integer>();
        var facultyCountMap = new HashMap<String, Integer>();
        var ageCountMap = new HashMap<String, Integer>();
        for (var student : internationalStudentsBySemester) {
            // Assigned/unassigned
            boolean hasBuddy = student.getAssignedBuddy() != null || Boolean.TRUE.equals(student.getAnonymizedHasBuddy());
            if (hasBuddy) {
                totalAssigned++;
            } else {
                totalUnassigned++;
            }
            // Country code
            String countryCode = Objects.toString(student.getCountryISO(), "");
            countrySet.add(countryCode);
            countryCountMap.put(countryCode, countryCountMap.getOrDefault(countryCode, 0) + 1);
            // Faculty ID
            String facultyId = Objects.nonNull(student.getFacultyId()) ? student.getFacultyId().toString() : "";
            facultyCountMap.put(facultyId, facultyCountMap.getOrDefault(facultyId, 0) + 1);
            // Age
            String ageStr = Objects.nonNull(student.getDateOfBirth())
                    ? String.valueOf(Period.between(student.getDateOfBirth(), LocalDate.now()).getYears())
                    : "";
            ageCountMap.put(ageStr, ageCountMap.getOrDefault(ageStr, 0) + 1);
        }
        result.setTotalInternationalStudents(internationalStudentsBySemester.size());
        result.setTotalAssignedInternationalStudents(totalAssigned);
        result.setTotalUnassignedInternationalStudents(totalUnassigned);
        result.setTotalInternationalStudentsCountries(countrySet.size());
        result.setTotalInternationalStudentsByCountryCode(countryCountMap);
        result.setTotalInternationalStudentsByFacultyId(facultyCountMap);
        result.setTotalInternationalStudentsByAge(ageCountMap);
        return result;
    }

    public UserManagementStatisticsLocalStudentActive getUserManagementStatisticsLocalStudentActive() {
        return new UserManagementStatisticsLocalStudentActive().totalActiveLocalStudents(localStudentRepository.countByIsActiveTrue());
    }
}
