package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.exception.TimeExpiredException;
import ru.kopyshev.rvs.repository.VoteRepository;
import ru.kopyshev.rvs.dto.VoteDTO;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_2;
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
    VoteRepository voteRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void voteUp() {
        VoteDTO actual = voteService.voteUp(USER, RESTAURANT_ID_1);
        VoteDTO expected = new VoteDTO(actual.id(), USER_ID, RESTAURANT_ID_1, LocalDate.now(), LocalTime.now());
        VOTE_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void cancelVote() {
        VoteDTO vote = voteService.voteUp(USER, RESTAURANT_ID_1);
        Assertions.assertNotNull(vote);
        voteService.cancelVote(USER_ID);
        Assertions.assertThrows(NotFoundException.class, () -> voteService.get(vote.id()));
    }

    @Test
    void get() {
        VoteDTO actual = voteService.get(VOTE_1_ID);
        VOTE_TO_MATCHER.assertMatch(actual, VOTE_DTO_1);
    }

    @Test
    void getAll() {
        List<VoteDTO> actual = List.of(VOTE_DTO_1, VOTE_DTO_3);
        List<VoteDTO> expected = voteService.getAll(USER_ID, null, null);
        VOTE_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getBetweenDates() {
        List<VoteDTO> expected = List.of(VOTE_DTO_1);
        List<VoteDTO> actual = voteService.getAll(USER_ID, DATE_1, DATE_1);
        VOTE_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void voteUpWhenExpired() {
        VoteDTO expected = voteServiceExpired.voteUp(USER, RESTAURANT_ID_1);
        VoteDTO actual = voteServiceExpired.get(expected.id());
        VOTE_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void voteAgainWhenExpired() {
        voteServiceExpired.voteUp(USER, RESTAURANT_ID_1);
        Assertions.assertThrows(TimeExpiredException.class, () -> voteServiceExpired.voteUp(USER, RESTAURANT_ID_2));
    }

    @Test
    void cancelWhenExpired() {
        voteServiceExpired.voteUp(USER, RESTAURANT_ID_1);
        Assertions.assertThrows(TimeExpiredException.class, () -> voteServiceExpired.cancelVote(USER_ID));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> voteService.get(NOT_FOUND_ID));
    }

    @Test
    void cancelNotExistingVote() {
        Assertions.assertThrows(NotFoundException.class, () -> voteService.cancelVote(USER_ID));
    }
}