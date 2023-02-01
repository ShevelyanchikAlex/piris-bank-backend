package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.FamilyStatus;
import com.bsuir.piris.model.dto.FamilyStatusDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FamilyStatusMapper {
    FamilyStatusDto toDto(FamilyStatus familyStatus);

    FamilyStatus toEntity(FamilyStatusDto familyStatusDto);
}
