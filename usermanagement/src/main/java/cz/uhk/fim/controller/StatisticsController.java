package cz.uhk.fim.controller;


import cz.uhk.fim.service.StatisticsService;
import cz.uhk.fim.usermanagement.api.StatisticsApi;
import cz.uhk.fim.usermanagement.model.UserManagementStatisticsInternationalStudentBySemester;
import cz.uhk.fim.usermanagement.model.UserManagementStatisticsLocalStudentActive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatisticsController implements StatisticsApi {

    private final StatisticsService statisticsService;

    @Override
    public ResponseEntity<UserManagementStatisticsInternationalStudentBySemester> getUserManagementStatisticsInternationalStudentBySemester(UUID semesterId) {
        return ResponseEntity.ok(statisticsService.getUserManagementStatisticsInternationalStudentBySemester(semesterId));
    }

    @Override
    public ResponseEntity<UserManagementStatisticsLocalStudentActive> getUserManagementStatisticsLocalStudentActive() {
        return ResponseEntity.ok(statisticsService.getUserManagementStatisticsLocalStudentActive());
    }
}
