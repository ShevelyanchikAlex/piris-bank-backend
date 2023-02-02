package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.*;
import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.model.mapper.UserMapper;
import com.bsuir.piris.persistence.*;
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
    private static final String CITY_NOT_FOUND_ERROR = "city.not.found";
    private static final String FAMILY_STATUS_NOT_FOUND_ERROR = "family_status.not.found";
    private static final String NATIONALITY_NOT_FOUND_ERROR = "nationality.not.found";
    private static final String DISABILITY_NOT_FOUND_ERROR = "disability.not.found";


    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final FamilyStatusRepository familyStatusRepository;
    private final NationalityRepository nationalityRepository;
    private final DisabilityRepository disabilityRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final IdValidator idValidator;

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        userValidator.validate(userDto);
        City city = cityRepository.findById(userDto.getCity().getId())
                .orElseThrow(() -> new ServiceException(CITY_NOT_FOUND_ERROR));
        FamilyStatus familyStatus = familyStatusRepository.findById(userDto.getFamilyStatus().getId())
                .orElseThrow(() -> new ServiceException(FAMILY_STATUS_NOT_FOUND_ERROR));
        Nationality nationality = nationalityRepository.findById(userDto.getNationality().getId())
                .orElseThrow(() -> new ServiceException(NATIONALITY_NOT_FOUND_ERROR));
        Disability disability = disabilityRepository.findById(userDto.getDisability().getId())
                .orElseThrow(() -> new ServiceException(DISABILITY_NOT_FOUND_ERROR));
        User entity = userMapper.toEntity(userDto);
        entity.setCity(city);
        entity.setFamilyStatus(familyStatus);
        entity.setNationality(nationality);
        entity.setDisability(disability);
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
