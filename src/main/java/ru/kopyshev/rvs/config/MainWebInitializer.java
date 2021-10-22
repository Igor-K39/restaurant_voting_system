package ru.kopyshev.rvs.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class MainWebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringDataJpaConfig.class);
        rootContext.register(SpringApplicationConfig.class);
        rootContext.register(SpringWebMvc.class);
        rootContext.setDisplayName("RVS");

        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);
        servletContext.addListener(contextLoaderListener);

        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("mvc-dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
