package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import ru.kopyshev.rvs.AbstractBaseTest;
import ru.kopyshev.rvs.util.ValidationUtil;

public class AbstractServiceTest extends AbstractBaseTest {

    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        Assertions.assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw ValidationUtil.getRootCause(e);
            }
        });
    }
}
