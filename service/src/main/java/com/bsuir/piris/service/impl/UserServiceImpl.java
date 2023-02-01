package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.User;
import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.model.mapper.UserMapper;
import com.bsuir.piris.persistence.UserRepository;
import com.bsuir.piris.service.UserService;
import com.bsuir.piris.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto save(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(entity);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException("not found"));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        List<UserDto> userDtoList = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(userDtoList, pageable, userRepository.count());
    }

    @Override
    public Long getUsersCount() {
        return userRepository.count();
    }
}
