package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.*;
import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.model.dto.UserPrepareDto;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_ERROR = "user.not.found";
    private static final String USER_EMAIL_EXIST_ERROR = "user.email.exits";
    private static final String USER_PASSPORT_NUMBER_EXIST_ERROR = "user.passport.number.exist";
    private static final String USER_PASSPORT_ID_EXIST_ERROR = "user.passport.id.exist";
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
        validateEmail(userDto.getEmail());
        validatePassport(userDto);
        userValidator.validate(userDto);
        UserPrepareDto userPrepareDto = getUserPrepareDto(userDto);
        User entity = userMapper.toEntity(userDto);
        entity.setCity(userPrepareDto.getCity());
        entity.setFamilyStatus(userPrepareDto.getFamilyStatus());
        entity.setNationality(userPrepareDto.getNationality());
        entity.setDisability(userPrepareDto.getDisability());
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
        validateUserById(userDto.getId());
        validateEmail(userDto.getEmail(), userDto.getId());
        validatePassport(userDto, userDto.getId());
        userValidator.validate(userDto);
        UserPrepareDto userUpdateDto = getUserPrepareDto(userDto);
        User entity = userMapper.toEntity(userDto);
        entity.setCity(userUpdateDto.getCity());
        entity.setFamilyStatus(userUpdateDto.getFamilyStatus());
        entity.setNationality(userUpdateDto.getNationality());
        entity.setDisability(userUpdateDto.getDisability());
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

    private void validateUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ServiceException(USER_NOT_FOUND_ERROR);
        }
    }

    private void validateEmail(String email) {
        if (Objects.nonNull(email) && userRepository.existsByEmail(email)) {
            throw new ServiceException(USER_EMAIL_EXIST_ERROR);
        }
    }

    private void validateEmail(String email, Long userId) {
        if (Objects.nonNull(email) && userRepository.existsByEmailAndIdIsNot(email, userId)) {
            throw new ServiceException(USER_EMAIL_EXIST_ERROR);
        }
    }

    private void validatePassport(UserDto userDto) {
        if (userRepository.existsByPassportId(userDto.getPassportId())) {
            throw new ServiceException(USER_PASSPORT_ID_EXIST_ERROR);
        }
        if (userRepository.existsByPassportNumber(userDto.getPassportNumber())) {
            throw new ServiceException(USER_PASSPORT_NUMBER_EXIST_ERROR);
        }
    }

    private void validatePassport(UserDto userDto, Long userId) {
        if (userRepository.existsByPassportIdAndIdIsNot(userDto.getPassportId(), userId)) {
            throw new ServiceException(USER_PASSPORT_ID_EXIST_ERROR);
        }
        if (userRepository.existsByPassportNumberAndIdIsNot(userDto.getPassportNumber(), userId)) {
            throw new ServiceException(USER_PASSPORT_NUMBER_EXIST_ERROR);
        }
    }

    private UserPrepareDto getUserPrepareDto(UserDto userDto) {
        City city = cityRepository.findById(userDto.getCity().getId())
                .orElseThrow(() -> new ServiceException(CITY_NOT_FOUND_ERROR));
        FamilyStatus familyStatus = familyStatusRepository.findById(userDto.getFamilyStatus().getId())
                .orElseThrow(() -> new ServiceException(FAMILY_STATUS_NOT_FOUND_ERROR));
        Nationality nationality = nationalityRepository.findById(userDto.getNationality().getId())
                .orElseThrow(() -> new ServiceException(NATIONALITY_NOT_FOUND_ERROR));
        Disability disability = disabilityRepository.findById(userDto.getDisability().getId())
                .orElseThrow(() -> new ServiceException(DISABILITY_NOT_FOUND_ERROR));
        return new UserPrepareDto(city, familyStatus, nationality, disability);
    }
}
