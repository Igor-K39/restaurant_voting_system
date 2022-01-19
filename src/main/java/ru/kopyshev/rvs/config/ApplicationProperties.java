package ru.kopyshev.rvs.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    // https://stackoverflow.com/questions/2065937/how-to-supply-value-to-an-annotation-from-a-constant-java
    public static final String WEB_PACKAGE = "ru.kopyshev.rvs.web";
    public static final String MODEL_PACKAGE = "ru.kopyshev.rvs.domain";
    public static final String SERVICE_PACKAGE = "ru.kopyshev.rvs.service";
    public static final String MAPPER_PACKAGE = "ru.kopyshev.rvs.util.mapper";
    public static final String JPA_REPOSITORIES_PACKAGE = "ru.kopyshev.rvs.repository";
    public static final String DATABASE_INIT_LOCATION = "db/initDB.sql";
    public static final String DATABASE_POPULATE_LOCATION = "db/populateDB.sql";

    private static final Properties properties = new Properties();
    static {
        try (InputStream is = ApplicationProperties.class.getResourceAsStream("/application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new ExceptionInInitializerError();
        }
    }

    public static final boolean JPA_SHOW_SQL = Boolean.parseBoolean(properties.getProperty("jpa.show_sql"));
    public static final boolean DATABASE_INIT = Boolean.parseBoolean(properties.getProperty("database.init"));

    public static final String DATABASE_URL = properties.getProperty("database.url");
    public static final String DRIVER_CLASS_NAME = properties.getProperty("database.driver");
    public static final String DATABASE_USERNAME = properties.getProperty("database.username");
    public static final String DATABASE_PASSWORD = properties.getProperty("database.password");

    public static final String HIBERNATE_FORMAT_SQL = properties.getProperty("hibernate.format_sql");
    public static final String HIBERNATE_USE_SQL_COMMENTS = properties.getProperty("hibernate.use_sql_comments");
    public static final String HIBERNATE_JPA_PROXY_COMPLIANCE = properties.getProperty("hibernate.jpa_proxy_compliance");
    public static final String HIBERNATE_CACHE_REGION_FACTORY = properties.getProperty("hibernate.cache.region.factory_class");
    public static final String HIBERNATE_JCACHE_PROVIDER = properties.getProperty("hibernate.jcache_provider");
    public static final String HIBERNATE_USE_SECOND_LEVEL_CACHE = properties.getProperty("hibernate.second_level_cache");
    public static final String HIBERNATE_USE_QUERY_CACHE = properties.getProperty("hibernate.query_cache");
    public static final String HIBERNATE_GENERATE_STATISTICS = properties.getProperty("hibernate.generate_statistics");

    public static final String HIBERNATE_JDBC_BATCH_SIZE = properties.getProperty("hibernate.jdbc_batch_size");
    public static final String HIBERNATE_ORDER_INSERTS = properties.getProperty("hibernate.order_inserts");
    public static final String HIBERNATE_ORDER_UPDATES = properties.getProperty("hibernate.order_updates");

    public static final String SECURITY_FILTER_NAME = properties.getProperty("spring.security.filter_name");
    public static final String VOTE_EXPIRATION_TIME = properties.getProperty("vote.expiration.time");

    public static final String EHCACHE_CONFIGURATION = properties.getProperty("ehcache.configuration");
}
