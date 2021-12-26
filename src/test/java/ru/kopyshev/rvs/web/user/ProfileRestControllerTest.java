package ru.kopyshev.rvs.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.UserService;
import ru.kopyshev.rvs.to.UserTo;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.util.UserUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.UserTestData.*;
import static ru.kopyshev.rvs.web.user.ProfileRestController.REST_URL;

public class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService service;

    @Test
    void create() throws Exception {
        var userTo = UserUtil.getToFromUser(getNew());
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .with(userHttpBasic(USER_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        var createdTo = USER_TO_MATCHER.readFromJson(actions);
        int id = createdTo.getId();
        userTo.setId(id);
        USER_TO_MATCHER.assertMatch(createdTo, userTo);
        USER_MATCHER.assertMatch(service.get(id), UserUtil.getUserFromTo(userTo));
    }

    @Test
    void get() throws Exception {
        UserTo expected  = UserUtil.getToFromUser(USER);
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(expected));
    }

    @Test
    void patch() throws Exception {
        UserTo expected = UserUtil.getToFromUser(USER);
        expected.setName(USER_UPDATED_NAME);
        expected.setPassword(USER_UPDATED_PASSWORD);
        expected.setEmail(USER_UPDATED_EMAIL);

        perform(MockMvcRequestBuilders.patch(REST_URL)
                .with(userHttpBasic(USER_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserTo actual = UserUtil.getToFromUser(service.get(USER_ID));
        USER_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(USER_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assertions.assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }
}
