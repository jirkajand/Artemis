package cz.uhk.fim.settingsservice.mapper;


import cz.uhk.fim.settingsservice.entity.FacultyEntity;
import cz.uhk.fim.settingsservice.model.FacultyCreateRequest;
import cz.uhk.fim.settingsservice.model.FacultyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper {


    FacultyResponse toFacultyResponse(FacultyEntity facultyEntity);

    FacultyEntity toFacultyEntity(FacultyCreateRequest facultyCreateRequest);
}
