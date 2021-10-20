package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.exception.TimeExpiredException;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.model.Vote;
import ru.kopyshev.rvs.repository.CrudVoteRepository;
import ru.kopyshev.rvs.util.CollectionUtil;
import ru.kopyshev.rvs.util.DateTimeUtil;
import ru.kopyshev.rvs.util.ValidationUtil;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

@Service
public class VoteService {
    private Clock clock = Clock.systemUTC();
    private final CrudVoteRepository voteRepository;
    private final LocalTime votingExpirationTime = LocalTime.of(11, 0);
    private final Predicate<LocalDateTime> canVoteUp =
            dt -> dt.isBefore(LocalDateTime.of(LocalDate.now(clock), votingExpirationTime));

    public VoteService(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public VoteService(CrudVoteRepository voteRepository, Clock clock) {
        this.voteRepository = voteRepository;
        this.clock = clock;
    }

    public Vote voteUp(User user, Restaurant restaurant) {
        var votes = voteRepository.getBetweenDates(user.id(), LocalDate.now(), LocalDate.now());
        if (votes.size() > 0) {
            assureVotingTimeNotExpired("Cannot change the vote after " + votingExpirationTime);
            var vote = votes.get(0);
            vote.setDate(LocalDate.now(clock));
            vote.setTime(LocalTime.now(clock));
            vote.setRestaurant(restaurant);
            return voteRepository.save(vote);
        } else {
            return voteRepository.save(new Vote(user, restaurant));
        }
    }

    public void cancelVote(int userId) {
        assureVotingTimeNotExpired("Can not cancel the vote after " + votingExpirationTime);
        var vote = voteRepository.delete(LocalDate.now(clock), userId);
        ValidationUtil.checkNotFound(vote != 0, "Cannot delete the vote of user " + userId);
    }

    public Vote get(int id) {
        return voteRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found vote with id = " + id));
    }

    public List<Vote> getAll(int userId) {
        return getAll(userId, null, null);
    }

    public List<Vote> getAll(int userId, LocalDate start, LocalDate end) {
        var startDate = DateTimeUtil.getMinIfNull(start);
        var endDate = DateTimeUtil.getMaxIfNull(end);
        var votes = voteRepository.getBetweenDates(userId, startDate, endDate);
        return CollectionUtil.getImmutableListIfNull(votes);
    }

    private void assureVotingTimeNotExpired(String message) {
        if (!canVoteUp.test(LocalDateTime.now(clock))) {
            throw new TimeExpiredException(message);
        }
    }
}