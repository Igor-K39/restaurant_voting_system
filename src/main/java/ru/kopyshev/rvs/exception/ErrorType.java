package ru.kopyshev.rvs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    APPLICATION_ERROR("error.application", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_ERROR("error.notFound", HttpStatus.UNPROCESSABLE_ENTITY),
    TIME_EXPIRED_ERROR("error.timeExpired", HttpStatus.FORBIDDEN),
    VALIDATION_ERROR("error.validationError", HttpStatus.CONFLICT),
    WRONG_REQUEST("error.wrongRequest", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus status;

    ErrorType(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

}
