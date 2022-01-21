package ru.kopyshev.rvs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.kopyshev.rvs.AbstractBaseTest;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class AbstractControllerTest extends AbstractBaseTest {

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
    public void postConstruct() {
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
