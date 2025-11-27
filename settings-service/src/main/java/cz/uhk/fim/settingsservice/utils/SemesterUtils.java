package cz.uhk.fim.settingsservice.utils;

import cz.uhk.fim.settingsservice.entity.enums.SemesterType;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class SemesterUtils {

    private static final String WINTER_SEMESTER_FORMAT = "Winter Semester %s";
    private static final String SUMMER_SEMESTER_FORMAT = "Summer Semester %s";

    public static String generateSemesterName(String year, SemesterType semesterType) {
        return switch (semesterType) {
            case SemesterType.WINTER -> String.format(WINTER_SEMESTER_FORMAT, year);
            case SemesterType.SUMMER -> String.format(SUMMER_SEMESTER_FORMAT, year);
        };
    }

    public static String generateCurrentYear(int currentYear, SemesterType semesterType) {
        return switch (semesterType) {
            case SemesterType.WINTER -> String.format("%s/%s", currentYear, currentYear + 1);
            case SemesterType.SUMMER -> String.format("%s/%s", currentYear - 1, currentYear);
        };
    }

    public static SemesterType getCurrentSemesterType(LocalDate now) {
        int month = now.getMonthValue();
        if (month >= 5 && month <= 10) {
            return SemesterType.WINTER;
        }
        return SemesterType.SUMMER;
    }
}
