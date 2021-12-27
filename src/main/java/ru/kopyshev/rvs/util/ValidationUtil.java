package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.HasId;
import ru.kopyshev.rvs.exception.NotFoundException;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        Assert.notNull(bean, "The bean must not be null");
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id == null)");
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id = " + id);
    }

    public static <T> T checkNotFound(T object, String message) {
        checkNotFound(object != null, message);
        return object;
    }

    public static void checkNotFound(boolean found, String message) {
        if (!found) {
            throw new NotFoundException("Not found entity: " + message);
        }
    }

    public static void assureIdConsistent(HasId hasId, int id) {
        Assert.notNull(hasId, "The hasId must not be null");
        if (hasId.isNew()) {
            hasId.setId(id);
        } else if (hasId.id() != id) {
            throw new IllegalArgumentException(hasId + " must be with id = " + id);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}