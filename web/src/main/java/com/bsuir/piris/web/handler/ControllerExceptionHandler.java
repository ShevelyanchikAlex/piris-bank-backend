package com.bsuir.piris.web.handler;

import com.bsuir.piris.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private static final String BAD_REQUEST_MESSAGE_KEY = "bad.request";
    private final MessageSource messageSource;

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(HttpServletRequest httpServletRequest, ServiceException e) {
        String errorMessage = messageSource.getMessage(e.getMessage(), new Object[]{}, getLocale(httpServletRequest));
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> badRequestError(HttpServletRequest httpServletRequest, Exception e) {
        String errorMessage = messageSource.getMessage(BAD_REQUEST_MESSAGE_KEY, new Object[]{}, getLocale(httpServletRequest));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private Locale getLocale(HttpServletRequest httpServletRequest) {
        String localeString = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return Objects.nonNull(localeString) ? Locale.forLanguageTag(localeString) : Locale.ENGLISH;
    }
}
