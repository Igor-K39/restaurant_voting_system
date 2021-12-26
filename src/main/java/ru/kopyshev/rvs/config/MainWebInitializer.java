package ru.kopyshev.rvs.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import static ru.kopyshev.rvs.config.ApplicationProperties.SECURITY_FILTER_NAME;

public class MainWebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);
        servletContext.addListener(contextLoaderListener);
        servletContext.addFilter(SECURITY_FILTER_NAME, new DelegatingFilterProxy(SECURITY_FILTER_NAME))
                .addMappingForUrlPatterns(null, true, "/*");

        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("mvc-dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        rootContext.register(SpringDataJpaConfig.class);
        rootContext.register(SpringApplicationConfig.class);
        rootContext.register(SpringWebMvc.class);
        rootContext.register(SpringSecurityConfig.class);
        rootContext.register(SpringFoxConfig.class);
        rootContext.setDisplayName("RVS");
    }
}
