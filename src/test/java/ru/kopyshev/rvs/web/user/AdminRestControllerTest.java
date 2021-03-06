package ru.kopyshev.rvs.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.dto.user.UserDTO;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.UserService;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.testdata.UserTestData.*;
import static ru.kopyshev.rvs.util.SecurityUtil.matches;

public class AdminRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = AdminRestController.ADMIN_REST_URL + '/';
    private static final UserDTO adminTo = ADMIN;
    private static final List<UserDTO> allDTO = Arrays.asList(ADMIN, USER);

    @Autowired
    private UserService service;

    @Test
    void create() throws Exception {
        UserDTO expected = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(restUrl)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        UserDTO actual = USER_TO_MATCHER.readFromJson(actions);
        expected.setId(actual.id());
        Assertions.assertTrue(matches(expected.getPassword(), actual.getPassword()));
        USER_TO_MATCHER.assertMatch(actual, expected);
        USER_TO_MATCHER.assertMatch(service.get(actual.id()), expected);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(restUrl + ADMIN_ID)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(adminTo));
    }

    @Test
    void getAll() throws Exception  {
        perform(MockMvcRequestBuilders.get(restUrl)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(allDTO));
    }

    @Test
    void getByEmail() throws Exception  {
        perform(MockMvcRequestBuilders.get(restUrl + "/by")
                .param("email", ADMIN.getEmail())
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(adminTo));
    }

    @Test
    void patch() throws Exception  {
        UserDTO expected = getUpdated(USER);

        perform(MockMvcRequestBuilders.patch(restUrl + USER_ID)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserDTO actual = service.get(expected.id());
        Assertions.assertTrue(matches(expected.getPassword(), actual.getPassword()));
        USER_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() throws Exception  {
        perform(MockMvcRequestBuilders.delete(restUrl + USER_ID)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assertions.assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }
}
