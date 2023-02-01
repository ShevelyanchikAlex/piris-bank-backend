package com.bsuir.piris.service.validator.impl;

import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.service.exception.ServiceException;
import com.bsuir.piris.service.validator.Validator;
import com.bsuir.piris.service.validator.ValidatorRegexPattern;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Predicate;

@Component
public class UserValidator implements Validator<UserDto> {
    private static final String USER_VALIDATE_ERROR = "user.validate.error";

    @Override
    public void validate(UserDto userDto) {
        if (Objects.isNull(userDto) || !(validateName(userDto.getName()) &&
                validateEmail(userDto.getEmail()))) {
            throw new ServiceException(USER_VALIDATE_ERROR);
        }
    }

    public boolean validateEmail(String email) {
        if (Objects.isNull(email)) {
            return false;
        }
        Predicate<String> userEmailPredicate = str -> str.matches(ValidatorRegexPattern.EMAIL_REGEX_PATTERN);
        return userEmailPredicate.test(email);
    }

    private boolean validateName(String name) {
        if (Objects.isNull(name)) {
            return false;
        }
        Predicate<String> userNamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        return userNamePredicate.test(name);
    }
}
