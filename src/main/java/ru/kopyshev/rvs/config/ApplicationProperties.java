package ru.kopyshev.rvs.config;

public class ApplicationProperties {

    public static final String WEB_PACKAGE = "ru.kopyshev.rvs.web";
    public static final String MODEL_PACKAGE = "ru.kopyshev.rvs.model";
    public static final String SERVICE_PACKAGE = "ru.kopyshev.rvs.service";
    public static final String JPA_REPOSITORIES_PACKAGE = "ru.kopyshev.rvs.repository";

    public static final String DATABASE_URL = "jdbc:hsqldb:mem:rvs";
    public static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
    public static final String DATABASE_USERNAME = "user";
    public static final String DATABASE_PASSWORD = "";

    public static final boolean JPA_SHOW_SQL = true;
    public static final boolean DATABASE_INIT = true;
    public static final String DATABASE_INIT_LOCATION = "db/init_hsqldb.sql";
    public static final String DATABASE_POPULATE_LOCATION = "db/populateDB.sql";

    public static final String HIBERNATE_FORMAT_SQL = "true";
    public static final String HIBERNATE_USE_SQL_COMMENTS = "true";
    public static final String HIBERNATE_JPA_PROXY_COMPLIANCE = "true";
    public static final String HIBERNATE_CACHE_REGION_FACTORY = "org.hibernate.cache.jcache.internal.JCacheRegionFactory";
    public static final String HIBERNATE_JCACHE_PROVIDER = "org.ehcache.jsr107.EhcacheCachingProvider";
    public static final String HIBERNATE_USE_SECOND_LEVEL_CACHE = "true";
    public static final String HIBERNATE_USE_QUERY_CACHE = "false";
    public static final String HIBERNATE_JDBC_BATCH_SIZE = "10";
    public static final String HIBERNATE_ORDER_INSERTS = "true";
    public static final String HIBERNATE_ORDER_UPDATES = "true";

    public static final String SECURITY_FILTER_NAME = "springSecurityFilterChain";
}
