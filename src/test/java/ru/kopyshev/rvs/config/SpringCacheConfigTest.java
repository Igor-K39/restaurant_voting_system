package ru.kopyshev.rvs.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class SpringCacheConfigTest {

    @Bean
    public CacheManager cacheManager() {
        return new NoOpCacheManager();
    }
}