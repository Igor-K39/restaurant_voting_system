package ru.kopyshev.rvs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import ru.kopyshev.rvs.repository.CrudVoteRepository;
import ru.kopyshev.rvs.service.VoteService;

import java.time.*;

@Configuration
@Import(SpringDataJpaConfig.class)
@ComponentScan(
        basePackages = ApplicationProperties.SERVICE_PACKAGE,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoteService.class)
        })
public class SpringApplicationConfigTest {
    @Autowired
    private CrudVoteRepository voteRepository;

    public static final Clock testClockNotExpired =
            Clock.fixed(LocalDateTime
                    .of(LocalDate.now(), LocalTime.of(10, 0))
                    .toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));

    public static final Clock testClockExpired =
            Clock.fixed(LocalDateTime
                    .of(LocalDate.now(), LocalTime.of(12, 0))
                    .toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));

    public SpringApplicationConfigTest(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Bean
    public VoteService voteService() {
        return new VoteService(voteRepository, testClockNotExpired);
    }


    @Bean
    public VoteService voteServiceExpired() {
        return new VoteService(voteRepository, testClockExpired);
    }
}
