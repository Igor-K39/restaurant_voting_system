package ru.kopyshev.rvs.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SpringDataJpaConfig.class)
@ComponentScan(basePackages = ApplicationProperties.SERVICE_PACKAGE)
public class SpringApplicationConfigTest {
}
