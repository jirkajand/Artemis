package cz.uhk.fim.settingsservice.service;

import cz.uhk.fim.settingsservice.entity.SemesterEntity;
import cz.uhk.fim.settingsservice.entity.enums.SemesterType;
import cz.uhk.fim.settingsservice.utils.SemesterUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SemesterScheduled {

    private final SemesterService semesterService;

    @PostConstruct
    public void init() {
        log.info("Check if there is any semester");
        if (semesterService.getAllSemesters().isEmpty()) {
            log.info("No semester found, creating current semester");
            SemesterEntity semesterEntity = new SemesterEntity();

            SemesterType currentSemesterType = SemesterUtils.getCurrentSemesterType(LocalDate.now());
            semesterEntity.setYear(SemesterUtils.generateCurrentYear(LocalDate.now().getYear(), currentSemesterType));
            semesterEntity.setSemesterName(SemesterUtils.generateSemesterName(semesterEntity.getYear(), currentSemesterType));
            semesterEntity.setSemesterType(currentSemesterType);

            //TESTING PURPOSE ONLY
            semesterEntity.setSemesterRegisterOpenDate(LocalDate.now().minusDays(1));

            if (semesterService.createSemester(semesterEntity).isPresent()) {
                log.info("Current semester created successfully");
            } else {
                log.error("Failed to create current semester");
            }
        }
    }

    @Scheduled(cron = "0 0 0 1 5 *")
    public void startOfWinterSemesterTask() {
        log.info("Starting semester scheduled task");

        if (semesterService.isAnySemesterCreatedToday()) {
            log.info("Semester for today is already created, skipping creation.");
            return;
        }

        SemesterEntity semesterEntity = new SemesterEntity();

        semesterEntity.setYear(SemesterUtils.generateCurrentYear(LocalDate.now().getYear(), SemesterType.WINTER));
        semesterEntity.setSemesterName(SemesterUtils.generateSemesterName(semesterEntity.getYear(), SemesterType.WINTER));
        semesterEntity.setSemesterType(SemesterType.WINTER);

        if (semesterService.createSemester(semesterEntity).isPresent()) {
            log.info("Winter semester created successfully");
        } else {
            log.error("Failed to create winter semester");
        }
    }

    @Scheduled(cron = "0 0 0 1 11 *")
    public void startOfSummerSemesterTask() {
        log.info("Starting semester scheduled task");

        if (semesterService.isAnySemesterCreatedToday()) {
            log.info("Semester for today is already created, skipping creation.");
            return;
        }
        
        SemesterEntity semesterEntity = new SemesterEntity();

        semesterEntity.setYear(SemesterUtils.generateCurrentYear(LocalDate.now().getYear(), SemesterType.SUMMER));
        semesterEntity.setSemesterName(SemesterUtils.generateSemesterName(semesterEntity.getYear(), SemesterType.SUMMER));
        semesterEntity.setSemesterType(SemesterType.SUMMER);

        if (semesterService.createSemester(semesterEntity).isPresent()) {
            log.info("Summer semester created successfully");
        } else {
            log.error("Failed to create summer semester");
        }
    }
}
