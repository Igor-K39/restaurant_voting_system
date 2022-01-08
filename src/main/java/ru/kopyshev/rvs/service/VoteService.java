package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.exception.TimeExpiredException;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.model.Vote;
import ru.kopyshev.rvs.repository.CrudRestaurantRepository;
import ru.kopyshev.rvs.repository.CrudVoteRepository;
import ru.kopyshev.rvs.to.VoteDTO;
import ru.kopyshev.rvs.util.ValidationUtil;
import ru.kopyshev.rvs.util.mapper.VoteMapper;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.isNull;
import static ru.kopyshev.rvs.config.ApplicationProperties.VOTE_EXPIRATION_TIME;
import static ru.kopyshev.rvs.util.DateTimeUtil.getMaxIfNull;
import static ru.kopyshev.rvs.util.DateTimeUtil.getMinIfNull;

@Service
public class VoteService {
    private Clock clock = Clock.systemUTC();
    private final CrudVoteRepository voteRepository;
    private final CrudRestaurantRepository restaurantRepository;
    private final VoteMapper voteMapper;
    private final LocalTime votingExpirationTime = LocalTime.parse(VOTE_EXPIRATION_TIME);
    private final Predicate<LocalDateTime> canVoteUp =
            dt -> dt.isBefore(LocalDateTime.of(LocalDate.now(clock), LocalTime.parse(VOTE_EXPIRATION_TIME)));


    public VoteService(CrudVoteRepository voteRepository, CrudRestaurantRepository restaurantRepository,
                       VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
    }

    public VoteService(CrudVoteRepository voteRepository, CrudRestaurantRepository restaurantRepository,
                       VoteMapper voteMapper, Clock clock) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
        this.clock = clock;
    }

    public VoteDTO voteUp(User user, int restaurantId) {
        if (voteRepository.hasSingleVote(user.id(), LocalDate.now())) {
            assureVotingTimeNotExpired("Cannot change the vote after " + votingExpirationTime);
        }
        Vote vote = voteRepository.voteUp(user, restaurantRepository.getById(restaurantId));
        return voteMapper.getDTO(vote);
    }

    public void cancelVote(int userId) {
        assureVotingTimeNotExpired("Can not cancel the vote after " + votingExpirationTime);
        int deletedRows = voteRepository.delete(LocalDate.now(clock), userId);
        ValidationUtil.checkNotFound(deletedRows != 0, "Cannot delete the vote of user " + userId);
    }

    public VoteDTO get(int id) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found vote with id = " + id));
        return voteMapper.getDTO(vote);
    }

    public VoteDTO get(int userId, LocalDate localDate) {
        Vote vote = voteRepository.getVoteOfUserByDate(userId, localDate).orElse(null);
        return !isNull(vote) ? voteMapper.getDTO(vote) : null;
    }

    public List<VoteDTO> getAll(int userId, LocalDate start, LocalDate end) {
        LocalDate startDate = getMinIfNull(start);
        LocalDate endDate = getMaxIfNull(end);
        List<Vote> votes = voteRepository.getBetweenDates(userId, startDate, endDate).orElse(List.of());
        return voteMapper.getDTO(votes);
    }

    private void assureVotingTimeNotExpired(String message) {
        if (!canVoteUp.test(LocalDateTime.now(clock))) {
            throw new TimeExpiredException(message);
        }
    }
}