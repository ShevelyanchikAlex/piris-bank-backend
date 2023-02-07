package com.bsuir.piris.service.validator.impl;

import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.service.exception.ServiceException;
import com.bsuir.piris.service.validator.Validator;
import com.bsuir.piris.service.validator.ValidatorRegexPattern;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class UserValidator implements Validator<UserDto> {
    private static final String USER_EMAIL_VALIDATE_ERROR = "user.email.validate.error";
    private static final String USER_NAME_VALIDATE_ERROR = "user.name.validate.error";
    private static final String USER_SURNAME_VALIDATE_ERROR = "user.surname.validate.error";
    private static final String USER_FATHERS_NAME_VALIDATE_ERROR = "user.fathersName.validate.error";
    private static final String USER_GENDER_VALIDATE_ERROR = "user.gender.validate.error";
    private static final String USER_PASSPORT_SERIES_VALIDATE_ERROR = "user.passport.series.validate.error";
    private static final String USER_PASSPORT_NUMBER_VALIDATE_ERROR = "user.passport.number.validate.error";
    private static final String USER_PASSPORT_ID_VALIDATE_ERROR = "user.passport.id.validate.error";
    private static final String USER_MOBILE_NUMBER_VALIDATE_ERROR = "user.mobile.number.validate.error";
    private static final String USER_DATE_VALIDATE_ERROR = "user.date.validate.error";
    private static final String USER_MONTHLY_INCOME_VALIDATE_ERROR = "user.monthly.income.validate.error";

    @Override
    public void validate(UserDto userDto) {
        validateEmail(userDto.getEmail());
        validateName(userDto.getName());
        validateSurname(userDto.getSurname());
        validateFathersName(userDto.getFathersName());
        validateGender(userDto.getGender());
        validatePassportSeries(userDto.getPassportSeries());
        validatePassportNumber(userDto.getPassportNumber());
        validatePassportId(userDto.getPassportId());
        validateMobileNumber(userDto.getMobileNumber());
        validateDate(userDto.getBirthday());
        validateDate(userDto.getPassportIssuedDate());
        validateMonthlyIncome(userDto.getMonthlyIncome());
    }

    public void validateEmail(String email) {
        Predicate<String> userEmailPredicate = str -> str.matches(ValidatorRegexPattern.EMAIL_REGEX_PATTERN);
        if (Objects.isNull(email) || !userEmailPredicate.test(email)) {
            throw new ServiceException(USER_EMAIL_VALIDATE_ERROR);
        }
    }

    private void validateName(String name) {
        Predicate<String> userNamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        if (Objects.isNull(name) || !userNamePredicate.test(name)) {
            throw new ServiceException(USER_NAME_VALIDATE_ERROR);
        }
    }

    private void validateSurname(String surname) {
        Predicate<String> userSurnamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        if (Objects.isNull(surname) || !userSurnamePredicate.test(surname)) {
            throw new ServiceException(USER_SURNAME_VALIDATE_ERROR);
        }
    }

    private void validateFathersName(String fathersName) {
        Predicate<String> userFathersNamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        if (Objects.isNull(fathersName) || !userFathersNamePredicate.test(fathersName)) {
            throw new ServiceException(USER_FATHERS_NAME_VALIDATE_ERROR);
        }
    }

    private void validateGender(Character gender) {
        Predicate<Character> userGenderPredicate = ch -> ch.equals('M') || ch.equals('F');
        if (Objects.isNull(gender) || !userGenderPredicate.test(gender)) {
            throw new ServiceException(USER_GENDER_VALIDATE_ERROR);
        }
    }

    private void validatePassportSeries(String passportSeries) {
        Predicate<String> userPassportSeriesPredicate = str -> str.matches(ValidatorRegexPattern.PASSPORT_SERIES_REGEX_PATTERN);
        if (Objects.isNull(passportSeries) || !userPassportSeriesPredicate.test(passportSeries)) {
            throw new ServiceException(USER_PASSPORT_SERIES_VALIDATE_ERROR);
        }
    }

    private void validatePassportNumber(String passportNumber) {
        Predicate<String> userPassportNumberPredicate = str -> str.matches(ValidatorRegexPattern.PASSPORT_NUMBER_REGEX_PATTERN);
        if (Objects.isNull(passportNumber) || !userPassportNumberPredicate.test(passportNumber)) {
            throw new ServiceException(USER_PASSPORT_NUMBER_VALIDATE_ERROR);
        }
    }

    private void validatePassportId(String passportId) {
        Predicate<String> userPassportIdPredicate = str -> str.matches(ValidatorRegexPattern.PASSPORT_ID_REGEX_PATTERN);
        if (Objects.isNull(passportId) || !userPassportIdPredicate.test(passportId)) {
            throw new ServiceException(USER_PASSPORT_ID_VALIDATE_ERROR);
        }
    }

    private void validateMobileNumber(String mobileNumber) {
        Predicate<String> userMobileNumberPredicate = str -> str.matches(ValidatorRegexPattern.MOBILE_NUMBER_REGEX_PATTERN);
        if (Objects.isNull(mobileNumber) || !userMobileNumberPredicate.test(mobileNumber)) {
            throw new ServiceException(USER_MOBILE_NUMBER_VALIDATE_ERROR);
        }
    }

    private void validateDate(LocalDate localDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate pastDate = LocalDate.parse("1900-01-01");
        if (Objects.isNull(localDate) || localDate.isBefore(pastDate) || localDate.isAfter(currentDate)) {
            throw new ServiceException(USER_DATE_VALIDATE_ERROR);
        }
    }

    private void validateMonthlyIncome(BigDecimal monthlyIncome) {
        if (monthlyIncome.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException(USER_MONTHLY_INCOME_VALIDATE_ERROR);
        }
    }
}
