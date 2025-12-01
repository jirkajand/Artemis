package cz.uhk.fim.mapper;

import cz.uhk.fim.entity.SemesterEntity;
import cz.uhk.fim.usermanagement.kafka.model.SemesterMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    SemesterEntity toEntity(SemesterMessage message);
}
