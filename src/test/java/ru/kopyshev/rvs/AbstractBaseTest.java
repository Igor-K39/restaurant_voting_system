package ru.kopyshev.rvs;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import ru.kopyshev.rvs.config.*;

import static ru.kopyshev.rvs.config.ApplicationProperties.DATABASE_INIT_LOCATION;
import static ru.kopyshev.rvs.config.ApplicationProperties.DATABASE_POPULATE_LOCATION;

@SpringJUnitWebConfig(
        classes = {
                SpringDataJpaConfig.class,
                SpringApplicationConfigTest.class,
                SpringWebMvc.class,
                SpringSecurityConfig.class,
                SpringCacheConfigTest.class
        })
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"),
        scripts = {
                "classpath:" + DATABASE_INIT_LOCATION,
                "classpath:" + DATABASE_POPULATE_LOCATION
        })
public class AbstractBaseTest {
}
