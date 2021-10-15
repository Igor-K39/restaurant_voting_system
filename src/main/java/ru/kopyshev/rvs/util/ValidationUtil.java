package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.BaseEntity;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(BaseEntity entity) {
        Assert.notNull(entity, "The entity must not be null");
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id == null)");
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

    public static void assureIdConsistent(BaseEntity entity, int id) {
        Assert.notNull(entity, "The entity must not be null");
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + " must be with id = " + id);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}