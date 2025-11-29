package cz.uhk.fim.mapper;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.entity.StudentEntity;
import cz.uhk.fim.usermanagement.model.ResponseStudentDetail;
import cz.uhk.fim.usermanagement.model.ResponseStudentNavbar;
import cz.uhk.fim.usermanagement.model.StudentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    ResponseStudentDetail fromLocalStudentEntity(LocalStudentEntity localStudentEntity);

    ResponseStudentDetail fromInternationalStudentEntity(InternationalStudentEntity internationalStudentEntity);

    @Mapping(target = "type", source = ".", qualifiedByName = "getTypeForStudentNavbar")
    ResponseStudentNavbar toResponseStudentNavbarFromInternationalStudent(InternationalStudentEntity internationalStudentEntity);

    @Mapping(target = "type", source = ".", qualifiedByName = "getTypeForStudentNavbar")
    ResponseStudentNavbar toResponseStudentNavbarFromLocalStudent(LocalStudentEntity localStudentEntity);


    @Named("getTypeForStudentNavbar")
    default StudentType getTypeForStudentNavbar(StudentEntity studentEntity) {
        if (studentEntity instanceof InternationalStudentEntity) {
            return StudentType.INTERNATIONAL;
        } else {
            return StudentType.LOCAL;
        }
    }
}
