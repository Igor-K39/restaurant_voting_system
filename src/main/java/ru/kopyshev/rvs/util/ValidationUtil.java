package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.HasId;
import ru.kopyshev.rvs.exception.ErrorType;
import ru.kopyshev.rvs.exception.IllegalRequestDataException;
import ru.kopyshev.rvs.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        Assert.notNull(bean, "The bean must not be null");
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id == null)");
        }
    }

    public static void checkNotFoundWithId(boolean found, Class<?> clazz, Integer id) {
        if (!found) {
            throw new NotFoundException(clazz, "id = " + id);
        }
    }

    public static <T> T checkNotFound(T bean, Class<?> clazz) {
        checkNotFoundWithId(bean != null, clazz, null);
        return bean;
    }

    public static void checkNotFound(boolean found, Class<?> clazz) {
        if (!found) {
            throw new NotFoundException(clazz, null);
        }
    }

    public static void assureIdConsistent(HasId hasId, int id) {
        Assert.notNull(hasId, "The hasId must not be null");
        if (hasId.isNew()) {
            hasId.setId(id);
        } else if (hasId.id() != id) {
            throw new IllegalRequestDataException(hasId + " must be with id = " + id);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static String getMessage(Throwable throwable) {
        String localizedMessage = throwable.getLocalizedMessage();
        return localizedMessage == null ? throwable.getMessage() : localizedMessage;
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest request, Exception exception,
                                               boolean logStackTrace, ErrorType errorType) {
        Throwable rootCause = getRootCause(exception);
        if (logStackTrace) {
            log.error("{} at request {} {}", errorType, request.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request {}: {}", errorType, request.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }
}