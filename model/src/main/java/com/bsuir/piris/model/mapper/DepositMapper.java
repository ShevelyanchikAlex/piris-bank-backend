package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.Deposit;
import com.bsuir.piris.model.dto.DepositDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AccountMapper.class})
public interface DepositMapper {
    DepositDto toDto(Deposit deposit);

    Deposit toEntity(DepositDto depositDto);
}
