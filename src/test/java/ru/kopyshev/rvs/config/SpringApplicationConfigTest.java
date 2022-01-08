package ru.kopyshev.rvs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import ru.kopyshev.rvs.repository.CrudRestaurantRepository;
import ru.kopyshev.rvs.repository.CrudVoteRepository;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.util.mapper.VoteMapper;

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
    private CrudVoteRepository voteRepository;

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Autowired
    private VoteMapper voteMapper;

    public static final Clock testClockNotExpired =
            Clock.fixed(LocalDateTime
                    .of(LocalDate.now(), LocalTime.parse(ApplicationProperties.VOTE_EXPIRATION_TIME).minusHours(1))
                    .toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));

    public static final Clock testClockExpired =
            Clock.fixed(LocalDateTime
                    .of(LocalDate.now(), LocalTime.parse(ApplicationProperties.VOTE_EXPIRATION_TIME).plusHours(1))
                    .toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));

    public SpringApplicationConfigTest(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Bean
    public VoteService voteService() {
        return new VoteService(voteRepository, restaurantRepository, voteMapper, testClockNotExpired);
    }

    @Bean
    public VoteService voteServiceExpired() {
        return new VoteService(voteRepository, restaurantRepository, voteMapper, testClockExpired);
    }
}
