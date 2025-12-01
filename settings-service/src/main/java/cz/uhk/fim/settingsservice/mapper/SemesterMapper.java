package cz.uhk.fim.settingsservice.mapper;


import cz.uhk.fim.settingsservice.entity.SemesterEntity;
import cz.uhk.fim.settingsservice.kafka.model.SemesterMessage;
import cz.uhk.fim.settingsservice.model.SemesterCreateRequest;
import cz.uhk.fim.settingsservice.model.SemesterResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    SemesterEntity toEntity(SemesterCreateRequest semesterCreateRequest);

    SemesterResponse toResponse(SemesterEntity semesterEntity);

    SemesterMessage toMessage(SemesterEntity semesterEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SemesterCreateRequest dto, @MappingTarget SemesterEntity entity);

}
