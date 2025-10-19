package cz.uhk.fim.mapper;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.service.RegisterKeycloakUserDTO;
import cz.uhk.fim.usermanagement.model.RegisterInternationalStudentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InternationalStudentMapper {

    RegisterKeycloakUserDTO toRegisterKeycloakUserDTO(RegisterInternationalStudentRequest request);

    InternationalStudentEntity toInternationalStudentEntity(RegisterInternationalStudentRequest request);
}
