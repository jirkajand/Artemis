package cz.uhk.fim.utils;

import cz.uhk.fim.entity.StudentEntity;
import cz.uhk.fim.usermanagement.model.InternationalStudentProfileEditRequest;
import cz.uhk.fim.usermanagement.model.LocalStudentProfileEditRequest;

public class KeycloakUtils {


    public static boolean isChangedKeycloakAttribute(InternationalStudentProfileEditRequest request, StudentEntity oldStudentEntity) {
        return checkKeycloakAttributes(oldStudentEntity, request.getEmail(), request.getFirstName(), request.getLastName());
    }

    public static boolean isChangedKeycloakAttribute(LocalStudentProfileEditRequest request, StudentEntity oldStudentEntity) {
        return checkKeycloakAttributes(oldStudentEntity, request.getEmail(), request.getFirstName(), request.getLastName());
    }

    private static boolean checkKeycloakAttributes(StudentEntity oldStudentEntity, String email, String firstName, String lastName) {
        if (email != null && !email.equals(oldStudentEntity.getEmail())) {
            return true;
        }
        if (firstName != null && !firstName.equals(oldStudentEntity.getFirstName())) {
            return true;
        }
        if (lastName != null && !lastName.equals(oldStudentEntity.getLastName())) {
            return true;
        }
        return false;
    }
}
