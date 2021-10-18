package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.kopyshev.rvs.config.SpringApplicationConfigTest;
import ru.kopyshev.rvs.config.SpringDataJpaConfig;
import ru.kopyshev.rvs.util.ValidationUtil;

import static ru.kopyshev.rvs.config.ApplicationProperties.DATABASE_INIT_LOCATION;
import static ru.kopyshev.rvs.config.ApplicationProperties.DATABASE_POPULATE_LOCATION;

@SpringJUnitConfig(value = {SpringDataJpaConfig.class, SpringApplicationConfigTest.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"),
        scripts = {"classpath:" + DATABASE_INIT_LOCATION, "classpath:" + DATABASE_POPULATE_LOCATION})
public class AbstractServiceTest {

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
