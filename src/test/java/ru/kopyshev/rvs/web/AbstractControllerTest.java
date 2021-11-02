package ru.kopyshev.rvs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.kopyshev.rvs.config.SpringApplicationConfigTest;
import ru.kopyshev.rvs.config.SpringDataJpaConfig;
import ru.kopyshev.rvs.config.SpringSecurityConfig;
import ru.kopyshev.rvs.config.SpringWebMvc;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static ru.kopyshev.rvs.config.ApplicationProperties.DATABASE_INIT_LOCATION;
import static ru.kopyshev.rvs.config.ApplicationProperties.DATABASE_POPULATE_LOCATION;

@SpringJUnitWebConfig(
        classes = {
                SpringDataJpaConfig.class,
                SpringApplicationConfigTest.class,
                SpringWebMvc.class,
                SpringSecurityConfig.class
        })
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"),
        scripts = {"classpath:" + DATABASE_INIT_LOCATION, "classpath:" + DATABASE_POPULATE_LOCATION})
public abstract class AbstractControllerTest {

    private static final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();

    @Autowired
    public Environment environment;

    static {
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
    }

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(characterEncodingFilter)
                .apply(springSecurity())
                .build();
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mvc.perform(builder);
    }
}
