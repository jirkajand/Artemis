package cz.uhk.fim.mapper;

import cz.uhk.fim.usermanagement.model.PageableResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageableMapper {

    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "totalElements", expression = "java((int) page.getTotalElements())")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "isLast", source = "last")
    PageableResponse toResponse(Page<?> page);
}
