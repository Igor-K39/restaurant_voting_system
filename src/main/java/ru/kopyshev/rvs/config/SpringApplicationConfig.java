package ru.kopyshev.rvs.config;

import org.springframework.context.annotation.*;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.repository.VoteRepository;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.util.mapper.VoteMapper;

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

    public SpringApplicationConfig(VoteRepository voteRepository, RestaurantRepository restaurantRepository,
                                   VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
    }

    @Bean
    public VoteService voteService() {
        return new VoteService(voteRepository, restaurantRepository, voteMapper);
    }
}