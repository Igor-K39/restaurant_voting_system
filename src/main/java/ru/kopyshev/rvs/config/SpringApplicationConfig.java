package ru.kopyshev.rvs.config;

import org.springframework.context.annotation.*;
import ru.kopyshev.rvs.repository.CrudVoteRepository;
import ru.kopyshev.rvs.service.VoteService;

@Configuration
@Import(SpringDataJpaConfig.class)
@ComponentScan(
        basePackages = {ApplicationProperties.MAPPER_PACKAGE, ApplicationProperties.SERVICE_PACKAGE},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoteService.class)
        }
)
public class SpringApplicationConfig {
    private final CrudVoteRepository voteRepository;

    public SpringApplicationConfig(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Bean
    public VoteService voteService() {
        return new VoteService(voteRepository);
    }
}