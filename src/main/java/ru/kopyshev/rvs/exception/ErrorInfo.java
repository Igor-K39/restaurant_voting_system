package ru.kopyshev.rvs.exception;

import lombok.Getter;

@Getter
public class ErrorInfo {
    private final String url;
    private final ErrorType errorType;
    private final String message;
    private final String[] details;

    public ErrorInfo(String url, ErrorType errorType, String message, String[] details) {
        this.url = url;
        this.errorType = errorType;
        this.message = message;
        this.details = details;
    }
}
