package ru.kopyshev.rvs.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.DelegatingFilterProxy;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.repository.VoteRepository;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.util.SecurityUtil;
import ru.kopyshev.rvs.util.mapper.UserMapper;
import ru.kopyshev.rvs.util.mapper.VoteMapper;

import javax.annotation.PostConstruct;

import static ru.kopyshev.rvs.config.ApplicationProperties.SECURITY_FILTER_NAME;

@Configuration
@Import(SpringDataJpaConfig.class)
@ComponentScan(
        basePackages = {ApplicationProperties.MAPPER_PACKAGE, ApplicationProperties.SERVICE_PACKAGE},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoteService.class)
        }
)
public class SpringApplicationConfig {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteMapper voteMapper;
    private final UserMapper userMapper;

    public SpringApplicationConfig(VoteRepository voteRepository, RestaurantRepository restaurantRepository,
                                   VoteMapper voteMapper, UserMapper userMapper) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
        this.userMapper = userMapper;
    }

    @Bean
    public VoteService voteService() {
        return new VoteService(voteRepository, restaurantRepository, voteMapper, userMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DelegatingFilterProxy delegatingFilterProxy() {
        return new DelegatingFilterProxy(SECURITY_FILTER_NAME);
    }

    @PostConstruct
    public void postConstruct() {
        SecurityUtil.initPasswordEncoder(passwordEncoder());
    }
}