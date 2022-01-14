package ru.kopyshev.rvs.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kopyshev.rvs.exception.*;

import javax.servlet.http.HttpServletRequest;

import static ru.kopyshev.rvs.exception.ErrorType.*;
import static ru.kopyshev.rvs.util.ValidationUtil.getMessage;
import static ru.kopyshev.rvs.util.ValidationUtil.logAndGetRootCause;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundException(HttpServletRequest request, NotFoundException exception) {
        return logAndGetErrorInfo(request, exception, false, NOT_FOUND_ERROR, exception.getDetails());
    }

    @ExceptionHandler(TimeExpiredException.class)
    public ResponseEntity<ErrorInfo> handleTimeExpiredException(HttpServletRequest request, TimeExpiredException exception) {
        return logAndGetErrorInfo(request, exception, false, TIME_EXPIRED_ERROR);
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    public ResponseEntity<ErrorInfo> handleIllegalRequestDataException(HttpServletRequest request, IllegalRequestDataException exception) {
        return logAndGetErrorInfo(request, exception, false, VALIDATION_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorInfo> handleBindException(HttpServletRequest request, BindException exception) {
        String[] details = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> " " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                .toArray(String[]::new);
        return logAndGetErrorInfo(request, exception, false, VALIDATION_ERROR, details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleIllegalRequestDataException(HttpServletRequest request, Exception exception) {
        return logAndGetErrorInfo(request, exception, true, APPLICATION_ERROR);
    }

    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest request, Exception exception,
                                                         boolean logStackTrace, ErrorType errorType, String... details) {
        Throwable rootCause = logAndGetRootCause(log, request, exception, logStackTrace, NOT_FOUND_ERROR);
        String url = request.getRequestURL().toString();
        ErrorInfo errorInfo = new ErrorInfo(url, errorType, errorType.getCode(), details.length == 0
                ? new String[]{getMessage(rootCause)}
                : details);
        return ResponseEntity.status(errorType.getStatus()).body(errorInfo);
    }
}
