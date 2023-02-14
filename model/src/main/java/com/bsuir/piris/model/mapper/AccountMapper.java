package com.bsuir.piris.model.mapper;

import com.bsuir.piris.model.domain.Account;
import com.bsuir.piris.model.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDto);
}
