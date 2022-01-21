package ru.kopyshev.rvs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.domain.Vote;
import ru.kopyshev.rvs.dto.VoteDTO;
import ru.kopyshev.rvs.dto.user.UserDTO;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.exception.TimeExpiredException;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.repository.VoteRepository;
import ru.kopyshev.rvs.util.ValidationUtil;
import ru.kopyshev.rvs.util.mapper.UserMapper;
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

@Slf4j
@Service
public class VoteService {
    private Clock clock = Clock.systemUTC();
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteMapper voteMapper;
    private final UserMapper userMapper;
    private final LocalTime votingExpirationTime = LocalTime.parse(VOTE_EXPIRATION_TIME);
    private final Predicate<LocalDateTime> canVoteUp =
            dt -> dt.isBefore(LocalDateTime.of(LocalDate.now(clock), LocalTime.parse(VOTE_EXPIRATION_TIME)));


    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository,
                       VoteMapper voteMapper, UserMapper userMapper) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
        this.userMapper = userMapper;
    }

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository,
                       VoteMapper voteMapper, UserMapper userMapper, Clock clock) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
        this.userMapper = userMapper;
        this.clock = clock;
    }

    public VoteDTO voteUp(UserDTO userDTO, Integer restaurantId) {
        log.debug("Voting up for restaurant {} by user {}", restaurantId, userDTO);
        if (voteRepository.hasSingleVote(userDTO.id(), LocalDate.now())) {
            assureVotingTimeNotExpired("Cannot change the vote after " + votingExpirationTime);
        }
        Vote vote = voteRepository.voteUp(userMapper.toEntity(userDTO), restaurantRepository.getById(restaurantId));
        return voteMapper.toDTO(vote);
    }

    public void cancelVote(Integer userId) {
        log.debug("Cancelling vote by user {} at date {}", userId, LocalDate.now());
        assureVotingTimeNotExpired("Can not cancel the vote after " + votingExpirationTime);
        int deletedRows = voteRepository.delete(LocalDate.now(), userId);
        ValidationUtil.checkNotFound(deletedRows != 0, Vote.class);
    }

    public VoteDTO get(Integer id) {
        log.debug("Getting a vote with id {}", id);
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new NotFoundException(Vote.class, "id = "));
        return voteMapper.toDTO(vote);
    }

    public VoteDTO get(int userId, LocalDate localDate) {
        log.debug("Getting a vote of user {} at date {}", userId, localDate);
        Vote vote = voteRepository.getVoteOfUserByDate(userId, localDate).orElse(null);
        return !isNull(vote) ? voteMapper.toDTO(vote) : null;
    }

    public List<VoteDTO> getAll(int userId, LocalDate start, LocalDate end) {
        log.debug("Getting all votes of user {} between {} and {}", userId, start, end);
        LocalDate startDate = getMinIfNull(start);
        LocalDate endDate = getMaxIfNull(end);
        List<Vote> votes = voteRepository.getBetweenDates(userId, startDate, endDate).orElse(List.of());
        return voteMapper.toDTO(votes);
    }

    private void assureVotingTimeNotExpired(String message) {
        LocalDateTime now = LocalDateTime.now(clock);
        log.debug("Checking if voting time is not expired: {}", now);
        if (!canVoteUp.test(now)) {
            throw new TimeExpiredException(message);
        }
    }
}