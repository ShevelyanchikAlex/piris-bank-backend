package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.ImaginaryTime;
import com.bsuir.piris.model.dto.ImaginaryTimeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImaginaryTimeMapper {
    ImaginaryTimeDto toDto(ImaginaryTime imaginaryTime);

    ImaginaryTime toEntity(ImaginaryTimeDto imaginaryTimeDto);
}
