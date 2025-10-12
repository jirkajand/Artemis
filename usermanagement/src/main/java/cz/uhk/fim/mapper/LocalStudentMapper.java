package cz.uhk.fim.mapper;


import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.service.RegisterKeycloakUserDTO;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalStudentMapper {

    RegisterKeycloakUserDTO toRegisterKeycloakUserDTO(RegisterLocalStudentRequest request);

    LocalStudentEntity toLocalStudentEntity(RegisterLocalStudentRequest request);
}
