package com.bsuir.piris.service.validator.impl;

import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.service.exception.ServiceException;
import com.bsuir.piris.service.validator.ValidateErrorConst;
import com.bsuir.piris.service.validator.Validator;
import com.bsuir.piris.service.validator.ValidatorRegexPattern;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class UserValidator implements Validator<UserDto> {

    @Override
    public void validate(UserDto userDto) {
        validateName(userDto.getName());
        validateSurname(userDto.getSurname());
        validateFathersName(userDto.getFathersName());
        validateGender(userDto.getGender());
        validatePassportSeries(userDto.getPassportSeries());
        validatePassportNumber(userDto.getPassportNumber());
        validatePassportId(userDto.getPassportId());
        validateDate(userDto.getBirthday());
        validateDate(userDto.getPassportIssuedDate());
        validateMonthlyIncome(userDto.getMonthlyIncome());
        validateMobileNumber(userDto.getMobileNumber());
        validatePhoneHomeNumber(userDto.getPhoneHomeNumber());
        validateEmail(userDto.getEmail());
    }

    private void validateEmail(String email) {
        Predicate<String> userEmailPredicate = str -> str.matches(ValidatorRegexPattern.EMAIL_REGEX_PATTERN);
        if (Objects.nonNull(email) && !userEmailPredicate.test(email)) {
            throw new ServiceException(ValidateErrorConst.USER_EMAIL_VALIDATE_ERROR);
        }
    }

    private void validateName(String name) {
        Predicate<String> userNamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        if (Objects.isNull(name) || !userNamePredicate.test(name)) {
            throw new ServiceException(ValidateErrorConst.USER_NAME_VALIDATE_ERROR);
        }
    }

    private void validateSurname(String surname) {
        Predicate<String> userSurnamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        if (Objects.isNull(surname) || !userSurnamePredicate.test(surname)) {
            throw new ServiceException(ValidateErrorConst.USER_SURNAME_VALIDATE_ERROR);
        }
    }

    private void validateFathersName(String fathersName) {
        Predicate<String> userFathersNamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        if (Objects.isNull(fathersName) || !userFathersNamePredicate.test(fathersName)) {
            throw new ServiceException(ValidateErrorConst.USER_FATHERS_NAME_VALIDATE_ERROR);
        }
    }

    private void validateGender(Character gender) {
        Predicate<Character> userGenderPredicate = ch -> ch.equals('M') || ch.equals('F');
        if (Objects.isNull(gender) || !userGenderPredicate.test(gender)) {
            throw new ServiceException(ValidateErrorConst.USER_GENDER_VALIDATE_ERROR);
        }
    }

    private void validatePassportSeries(String passportSeries) {
        Predicate<String> userPassportSeriesPredicate = str -> str.matches(ValidatorRegexPattern.PASSPORT_SERIES_REGEX_PATTERN);
        if (Objects.isNull(passportSeries) || !userPassportSeriesPredicate.test(passportSeries)) {
            throw new ServiceException(ValidateErrorConst.USER_PASSPORT_SERIES_VALIDATE_ERROR);
        }
    }

    private void validatePassportNumber(String passportNumber) {
        Predicate<String> userPassportNumberPredicate = str -> str.matches(ValidatorRegexPattern.PASSPORT_NUMBER_REGEX_PATTERN);
        if (Objects.isNull(passportNumber) || !userPassportNumberPredicate.test(passportNumber)) {
            throw new ServiceException(ValidateErrorConst.USER_PASSPORT_NUMBER_VALIDATE_ERROR);
        }
    }

    private void validatePassportId(String passportId) {
        Predicate<String> userPassportIdPredicate = str -> str.matches(ValidatorRegexPattern.PASSPORT_ID_REGEX_PATTERN);
        if (Objects.isNull(passportId) || !userPassportIdPredicate.test(passportId)) {
            throw new ServiceException(ValidateErrorConst.USER_PASSPORT_ID_VALIDATE_ERROR);
        }
    }

    private void validateMobileNumber(String mobileNumber) {
        Predicate<String> userMobileNumberPredicate = str -> str.matches(ValidatorRegexPattern.MOBILE_NUMBER_REGEX_PATTERN);
        if (Objects.nonNull(mobileNumber) && !userMobileNumberPredicate.test(mobileNumber)) {
            throw new ServiceException(ValidateErrorConst.USER_MOBILE_NUMBER_VALIDATE_ERROR);
        }
    }

    private void validatePhoneHomeNumber(String phoneHomeNumber) {
        Predicate<String> userPhoneHomeNumber = str -> str.matches(ValidatorRegexPattern.PHONE_HOME_NUMBER_REGEX_PATTERN);
        if (Objects.nonNull(phoneHomeNumber) && !userPhoneHomeNumber.test(phoneHomeNumber)) {
            throw new ServiceException(ValidateErrorConst.USER_PHONE_HOME_NUMBER_VALIDATE_ERROR);
        }
    }

    private void validateDate(LocalDate localDate) {
        String MIN_DATE = "1900-01-01";
        LocalDate currentDate = LocalDate.now();
        LocalDate pastDate = LocalDate.parse(MIN_DATE);
        if (Objects.isNull(localDate) || localDate.isBefore(pastDate) || localDate.isAfter(currentDate)) {
            throw new ServiceException(ValidateErrorConst.USER_DATE_VALIDATE_ERROR);
        }
    }

    private void validateMonthlyIncome(BigDecimal monthlyIncome) {
        if (Objects.nonNull(monthlyIncome) && monthlyIncome.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException(ValidateErrorConst.USER_MONTHLY_INCOME_VALIDATE_ERROR);
        }
    }
}
