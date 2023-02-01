package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.User;
import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.model.mapper.UserMapper;
import com.bsuir.piris.persistence.UserRepository;
import com.bsuir.piris.service.UserService;
import com.bsuir.piris.service.exception.ServiceException;
import com.bsuir.piris.service.validator.impl.IdValidator;
import com.bsuir.piris.service.validator.impl.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_ERROR = "user.not.found";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final IdValidator idValidator;

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        userValidator.validate(userDto);
        User entity = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(entity);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto findById(Long id) {
        idValidator.validate(id);
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND_ERROR));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        List<UserDto> userDtoList = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(userDtoList, pageable, userRepository.count());
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto) {
        userValidator.validate(userDto);
        if (!userRepository.existsById(userDto.getId())) {
            throw new ServiceException(USER_NOT_FOUND_ERROR);
        }
        User entity = userMapper.toEntity(userDto);
        User updatedUser = userRepository.save(entity);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        idValidator.validate(id);
        if (!userRepository.existsById(id)) {
            throw new ServiceException(USER_NOT_FOUND_ERROR);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Long getUsersCount() {
        return userRepository.count();
    }
}
