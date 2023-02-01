package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.Disability;
import com.bsuir.piris.model.dto.DisabilityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisabilityMapper {
    DisabilityDto toDto(Disability disability);

    Disability toEntity(DisabilityDto disabilityDto);
}
