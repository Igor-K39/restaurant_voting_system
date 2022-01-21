package ru.kopyshev.rvs.exception;

public class IllegalRequestDataException extends RuntimeException {
    public IllegalRequestDataException(String message) {
        super(message);
    }
}
