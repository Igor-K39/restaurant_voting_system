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
        UserDTO userDTO = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .with(userHttpBasic(USER_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        UserDTO createdTo = USER_TO_MATCHER.readFromJson(actions);
        int id = createdTo.getId();
        userDTO.setId(id);
        userDTO.setPassword(createdTo.getPassword()); //FIXME password encoded, but always different
        USER_TO_MATCHER.assertMatch(createdTo, userDTO);
        USER_TO_MATCHER.assertMatch(service.get(id), userDTO);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(USER));
    }

    @Test
    void patch() throws Exception {
        UserDTO expected = new UserDTO(USER);
        expected.setName(USER_UPDATED_NAME);
        expected.setPassword(USER_UPDATED_PASSWORD);
        expected.setEmail(USER_UPDATED_EMAIL);

        perform(MockMvcRequestBuilders.patch(REST_URL)
                .with(userHttpBasic(USER_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserDTO actual = service.get(USER_ID);
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
