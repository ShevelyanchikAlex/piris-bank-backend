package com.bsuir.piris.service;

import com.bsuir.piris.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto save(UserDto userDto);

    UserDto findById(Long id);

    Page<UserDto> findAll(Pageable pageable);

    Long getUsersCount();
}
