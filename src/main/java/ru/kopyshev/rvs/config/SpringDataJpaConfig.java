package ru.kopyshev.rvs.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.cache.jcache.ConfigSettings;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

import static ru.kopyshev.rvs.config.ApplicationProperties.*;

@Configuration
@ComponentScan(basePackages = JPA_REPOSITORIES_PACKAGE)
@EnableJpaRepositories(basePackages = JPA_REPOSITORIES_PACKAGE)
@EnableTransactionManagement
public class SpringDataJpaConfig {

    @Bean
    public DataSource dataSource() {
        var dataSource = new DataSource();
        dataSource.setUrl(DATABASE_URL);
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUsername(DATABASE_USERNAME);
        dataSource.setPassword(DATABASE_PASSWORD);
        return dataSource;
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        var populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource(DATABASE_INIT_LOCATION));
        populator.addScript(new ClassPathResource(DATABASE_POPULATE_LOCATION));
        return populator;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        var initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource());
        initializer.setDatabasePopulator(databasePopulator());
        initializer.setEnabled(DATABASE_INIT);
        return initializer;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        var adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(JPA_SHOW_SQL);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(MODEL_PACKAGE);
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());

        var jpaPropertyMap = new HashMap<String, String>();
        jpaPropertyMap.put(AvailableSettings.FORMAT_SQL, HIBERNATE_FORMAT_SQL);
        jpaPropertyMap.put(AvailableSettings.USE_SQL_COMMENTS, HIBERNATE_USE_SQL_COMMENTS);
        jpaPropertyMap.put(AvailableSettings.JPA_PROXY_COMPLIANCE, HIBERNATE_JPA_PROXY_COMPLIANCE);
        jpaPropertyMap.put(AvailableSettings.CACHE_REGION_FACTORY, HIBERNATE_CACHE_REGION_FACTORY);
        jpaPropertyMap.put(ConfigSettings.PROVIDER, HIBERNATE_JCACHE_PROVIDER);
        jpaPropertyMap.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, HIBERNATE_USE_SECOND_LEVEL_CACHE);
        jpaPropertyMap.put(AvailableSettings.USE_QUERY_CACHE, HIBERNATE_USE_QUERY_CACHE);
        jpaPropertyMap.put(AvailableSettings.STATEMENT_BATCH_SIZE, HIBERNATE_JDBC_BATCH_SIZE);
        jpaPropertyMap.put(AvailableSettings.ORDER_INSERTS, HIBERNATE_ORDER_INSERTS);
        jpaPropertyMap.put(AvailableSettings.ORDER_UPDATES, HIBERNATE_ORDER_UPDATES);
        factoryBean.setJpaPropertyMap(jpaPropertyMap);
        return factoryBean;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager dbTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
