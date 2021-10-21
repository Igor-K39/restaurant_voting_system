package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.kopyshev.rvs.VoteTestData;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.exception.TimeExpiredException;
import ru.kopyshev.rvs.model.Vote;
import ru.kopyshev.rvs.repository.CrudVoteRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.TestData.DATE_1;
import static ru.kopyshev.rvs.TestData.NOT_FOUND_ID;
import static ru.kopyshev.rvs.UserTestData.USER;
import static ru.kopyshev.rvs.UserTestData.USER_ID;
import static ru.kopyshev.rvs.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    @Qualifier("voteService")
    VoteService voteService;

    @Autowired
    @Qualifier("voteServiceExpired")
    VoteService voteServiceExpired;

    @Autowired
    CrudVoteRepository voteRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void voteUp() {
        var votes = voteService.getAll(USER_ID, LocalDate.now(), LocalDate.now());
        Assertions.assertEquals(0, votes.size());
        var expected = voteService.voteUp(USER, RESTAURANT_1);
        var actual = new Vote(expected.id(), LocalDate.now(), expected.getTime(), USER, RESTAURANT_1);
        VOTE_MATCHER.assertMatch(expected, actual);
    }

    @Test
    void cancelVote() {
        var vote = voteService.voteUp(USER, RESTAURANT_1);
        Assertions.assertNotNull(vote);
        voteService.cancelVote(USER_ID);
        Assertions.assertThrows(NotFoundException.class, () -> voteService.get(vote.id()));
    }

    @Test
    void get() {
        var actual = voteService.get(VOTE_1_ID);
        VOTE_MATCHER.assertMatch(actual, VoteTestData.VOTE_1);
    }

    @Test
    void getAll() {
        var actual = List.of(VOTE_1, VOTE_3);
        var expected = voteService.getAll(USER_ID);
        VOTE_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getBetweenDates() {
        var expected = List.of(VOTE_1);
        var actual = voteService.getAll(USER_ID, DATE_1, DATE_1);
        VOTE_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void voteUpWhenCannotVoteAgain() {
        var expected = voteServiceExpired.voteUp(USER, RESTAURANT_1);
        var actual = voteServiceExpired.get(expected.id());
        VOTE_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void voteAgainWhenExpired() {
        voteServiceExpired.voteUp(USER, RESTAURANT_1);
        Assertions.assertThrows(TimeExpiredException.class, () -> voteServiceExpired.voteUp(USER, RESTAURANT_2));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> voteService.get(NOT_FOUND_ID));
    }

    @Test
    void cancelNotExistingVote() {
        Assertions.assertThrows(TimeExpiredException.class, () -> voteServiceExpired.cancelVote(USER_ID));
    }
}