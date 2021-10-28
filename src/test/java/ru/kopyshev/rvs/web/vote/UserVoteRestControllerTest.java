package ru.kopyshev.rvs.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.util.VoteUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_1;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.TestData.DATE_1;
import static ru.kopyshev.rvs.UserTestData.USER;
import static ru.kopyshev.rvs.UserTestData.USER_ID;
import static ru.kopyshev.rvs.VoteTestData.VOTE_TO_MATCHER;

class UserVoteRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = UserVoteRestController.USER_REST_URL;

    @Autowired
    @Qualifier(value = "voteService")
    VoteService service;

    @Test
    void voteUp() throws Exception {
        perform(MockMvcRequestBuilders.post(restUrl)
                .param("restaurant", String.valueOf(RESTAURANT_ID_1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        var votes = service.getAll(USER_ID, LocalDate.now(), LocalDate.now());
        Assertions.assertEquals(1, votes.size());
        Assertions.assertEquals(RESTAURANT_ID_1, votes.get(0).getRestaurant().id());
    }

    @Test
    void cancelVote() throws Exception {
        int id = service.voteUp(USER, RESTAURANT_1).id();
        perform(MockMvcRequestBuilders.post(restUrl + "/cancel"))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(id));
    }

    @Test
    void getAll() throws Exception {
        var votes = service.getAll(USER_ID, DATE_1, DATE_1);
        perform(MockMvcRequestBuilders.get(restUrl)
                .param("start", DATE_1.toString())
                .param("end", DATE_1.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.getToFromVote(votes)));
    }
}