package ru.kopyshev.rvs.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.UserTestData;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.UserService;
import ru.kopyshev.rvs.to.UserTo;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.util.UserUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.UserTestData.*;

public class AdminRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = AdminRestController.ADMIN_REST_URL + '/';
    private static final UserTo adminTo = UserUtil.getToFromUser(ADMIN);
    private static final List<UserTo> allTo = UserUtil.getToFromUser(Arrays.asList(ADMIN, USER));

    @Autowired
    private UserService service;

    @Test
    void create() throws Exception {
        var userTo = UserUtil.getToFromUser(UserTestData.getNew());
        ResultActions actions = perform(MockMvcRequestBuilders.post(restUrl)
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
        perform(MockMvcRequestBuilders.get(restUrl + ADMIN_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(adminTo));
    }

    @Test
    void getAll() throws Exception  {
        perform(MockMvcRequestBuilders.get(restUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(allTo));
    }

    @Test
    void getByEmail() throws Exception  {
        perform(MockMvcRequestBuilders.get(restUrl + "/by")
                .param("email", ADMIN.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(adminTo));
    }

    @Test
    void patch() throws Exception  {
        UserTo updated = UserUtil.getToFromUser(UserTestData.getUpdated(USER));

        perform(MockMvcRequestBuilders.patch(restUrl + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserTo actual = UserUtil.getToFromUser(service.get(USER_ID));
        USER_TO_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void delete() throws Exception  {
        perform(MockMvcRequestBuilders.delete(restUrl + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assertions.assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }
}
