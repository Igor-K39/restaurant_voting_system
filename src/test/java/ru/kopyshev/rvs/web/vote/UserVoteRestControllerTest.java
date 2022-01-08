package ru.kopyshev.rvs.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.to.VoteDTO;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.TestData.DATE_1;
import static ru.kopyshev.rvs.TestData.DATE_2;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.UserTestData.*;
import static ru.kopyshev.rvs.VoteTestData.*;
import static ru.kopyshev.rvs.web.vote.UserVoteRestController.USER_REST_URL;

class UserVoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    @Qualifier(value = "voteService")
    VoteService service;

    @Test
    void voteUp() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.post(USER_REST_URL)
                .param("restaurant", String.valueOf(RESTAURANT_ID_1))
                .with(userHttpBasic(USER_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());

        VoteDTO created = VOTE_TO_MATCHER.readFromJson(actions);
        VoteDTO expected = service.get(created.id());
        VOTE_TO_MATCHER.assertMatch(created, expected);
    }

    @Test
    void cancelVote() throws Exception {
        int id = service.voteUp(USER, RESTAURANT_ID_1).id();
        perform(MockMvcRequestBuilders.post(USER_REST_URL + "/cancel")
                .with(userHttpBasic(USER_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(id));
    }

    @Test
    void getOnDate() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_REST_URL + "/of")
                .param("date", DATE_1.toString())
                .with(userHttpBasic(USER_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_DTO_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(USER_REST_URL)
                .param("start", DATE_1.toString())
                .param("end", DATE_2.toString())
                .with(userHttpBasic(USER_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(VOTE_DTO_1, VOTE_DTO_3)));
    }
}