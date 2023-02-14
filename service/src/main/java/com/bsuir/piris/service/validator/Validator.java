package com.bsuir.piris.service.validator;

public interface Validator<T> {
    void validate(T entity);
}