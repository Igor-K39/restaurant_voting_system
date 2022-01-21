package ru.kopyshev.rvs.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final String entityName;
    private final String details;

    public NotFoundException(Class<?> clazz, String details) {
        super("Not found " + clazz.getSimpleName() + ": " + details);
        this.entityName = clazz.getSimpleName();
        this.details = details;
    }
}