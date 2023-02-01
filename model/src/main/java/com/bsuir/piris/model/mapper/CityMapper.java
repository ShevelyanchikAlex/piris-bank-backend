package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.City;
import com.bsuir.piris.model.dto.CityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDto toDto(City city);

    City toEntity(CityDto cityDto);
}
