package com.bsuir.piris.service.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorRegexPattern {
    public static final String USER_NAME_REGEX_PATTERN = "^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$";
    public static final String EMAIL_REGEX_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String PASSPORT_SERIES_REGEX_PATTERN = "^([A-Z]{2})$";
    public static final String PASSPORT_NUMBER_REGEX_PATTERN = "^([0-9]{7})$";
    public static final String PASSPORT_ID_REGEX_PATTERN = "^([A-Z0-9]{14})$";
    public static final String MOBILE_NUMBER_REGEX_PATTERN = "^\\+375(17|29|33|44)[0-9]{7}$";

}