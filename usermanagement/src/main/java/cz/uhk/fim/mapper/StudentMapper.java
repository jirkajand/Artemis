package cz.uhk.fim.mapper;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.usermanagement.model.ResponseStudentDetail;
import cz.uhk.fim.usermanagement.model.ResponseStudentNavbar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    ResponseStudentDetail fromLocalStudentEntity(LocalStudentEntity localStudentEntity);

    ResponseStudentDetail fromInternationalStudentEntity(InternationalStudentEntity internationalStudentEntity);

    ResponseStudentNavbar toResponseStudentNavbarFromInternationalStudent(InternationalStudentEntity internationalStudentEntity);

    ResponseStudentNavbar toResponseStudentNavbarFromLocalStudent(LocalStudentEntity localStudentEntity);
}
