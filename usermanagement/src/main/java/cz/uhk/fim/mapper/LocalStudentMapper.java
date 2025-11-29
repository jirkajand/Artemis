package cz.uhk.fim.mapper;


import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.service.RegisterKeycloakUserDTO;
import cz.uhk.fim.usermanagement.model.LocalStudentProfile;
import cz.uhk.fim.usermanagement.model.LocalStudentProfileEditRequest;
import cz.uhk.fim.usermanagement.model.RegisterLocalStudentRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LocalStudentMapper {

    RegisterKeycloakUserDTO toRegisterKeycloakUserDTO(RegisterLocalStudentRequest request);

    LocalStudentEntity toLocalStudentEntity(RegisterLocalStudentRequest request);

    LocalStudentProfile toLocalStudentProfile(LocalStudentEntity localStudent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLocalStudentFromEditRequest(LocalStudentProfileEditRequest localStudentProfileEditRequest, @MappingTarget LocalStudentEntity localStudent);
}
