package ru.kopyshev.rvs.exception;

public class TimeExpiredException extends RuntimeException {

    public TimeExpiredException(String message) {
        super(message);
    }
}