package cz.uhk.fim.mapper;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.service.RegisterKeycloakUserDTO;
import cz.uhk.fim.usermanagement.model.AssignedInternationalStudent;
import cz.uhk.fim.usermanagement.model.InternationalStudentAnonymous;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InternationalStudentMapper {

    RegisterKeycloakUserDTO toRegisterKeycloakUserDTO(RegisterInternationalStudentRequest request);

    InternationalStudentEntity toInternationalStudentEntity(RegisterInternationalStudentRequest request);

    @Mapping(target = "countryCode", source = "countryISO")
    @Mapping(target = "isAssigned", expression = "java(entity.getAssignedBuddy() != null)")
    InternationalStudentAnonymous toInternationalStudentAnonymous(InternationalStudentEntity entity);

    @Mapping(target = "countryCode", source = "countryISO")
    AssignedInternationalStudent toAssignedInternationalStudent(InternationalStudentEntity internationalStudentEntity);
}
