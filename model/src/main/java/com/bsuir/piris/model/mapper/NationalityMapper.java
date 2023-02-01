package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.Nationality;
import com.bsuir.piris.model.dto.NationalityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NationalityMapper {
    NationalityDto toDto(Nationality nationality);

    Nationality toEntity(NationalityDto nationalityDto);
}
