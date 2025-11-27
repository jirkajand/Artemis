package cz.uhk.fim.settingsservice.mapper;


import cz.uhk.fim.settingsservice.entity.SemesterEntity;
import cz.uhk.fim.settingsservice.model.SemesterCreateRequest;
import cz.uhk.fim.settingsservice.model.SemesterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    SemesterEntity toEntity(SemesterCreateRequest semesterCreateRequest);

    SemesterResponse toResponse(SemesterEntity semesterEntity);
}
