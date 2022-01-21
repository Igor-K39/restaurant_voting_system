package ru.kopyshev.rvs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.repository.VoteRepository;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.util.SecurityUtil;
import ru.kopyshev.rvs.util.mapper.UserMapper;
import ru.kopyshev.rvs.util.mapper.VoteMapper;

import javax.annotation.PostConstruct;
import java.time.*;

@Configuration
@Import(SpringDataJpaConfig.class)
@ComponentScan(
        basePackages = {
                ApplicationProperties.MAPPER_PACKAGE,
                ApplicationProperties.SERVICE_PACKAGE
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoteService.class)
        })
public class SpringApplicationConfigTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private UserMapper userMapper;

    public static final Clock testClockNotExpired =
            Clock.fixed(LocalDateTime
                    .of(LocalDate.now(), LocalTime.parse(ApplicationProperties.VOTE_EXPIRATION_TIME).minusHours(1))
                    .toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));

    public static final Clock testClockExpired =
            Clock.fixed(LocalDateTime
                    .of(LocalDate.now(), LocalTime.parse(ApplicationProperties.VOTE_EXPIRATION_TIME).plusHours(1))
                    .toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));

    public SpringApplicationConfigTest(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Bean
    public VoteService voteService() {
        return new VoteService(voteRepository, restaurantRepository, voteMapper, userMapper, testClockNotExpired);
    }

    @Bean
    public VoteService voteServiceExpired() {
        return new VoteService(voteRepository, restaurantRepository, voteMapper, userMapper, testClockExpired);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void postConstruct() {
        SecurityUtil.initPasswordEncoder(passwordEncoder());
    }
}
