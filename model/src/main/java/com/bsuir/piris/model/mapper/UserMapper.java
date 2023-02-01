package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.User;
import com.bsuir.piris.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CityMapper.class, DisabilityMapper.class,
        FamilyStatusMapper.class, NationalityMapper.class})
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
