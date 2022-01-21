package ru.kopyshev.rvs.web.json;

import org.junit.jupiter.api.Test;
import ru.kopyshev.rvs.dto.user.UserDTO;
import ru.kopyshev.rvs.util.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static ru.kopyshev.rvs.UserTestData.*;

public class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(USER);
        System.out.println(json);
        UserDTO user = JsonUtil.readValue(json, UserDTO.class);
        USER_TO_MATCHER.assertMatch(user, USER);
    }

    @Test
    void readWriteValues() {
        List<UserDTO> expected = Arrays.asList(USER, ADMIN);
        String json = JsonUtil.writeValue(expected);
        List<UserDTO> actual = JsonUtil.readValues(json, UserDTO.class);
        USER_TO_MATCHER.assertMatch(actual, expected);
    }
}
