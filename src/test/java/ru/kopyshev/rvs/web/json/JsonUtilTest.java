package ru.kopyshev.rvs.web.json;

import org.junit.jupiter.api.Test;
import ru.kopyshev.rvs.domain.User;
import ru.kopyshev.rvs.util.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static ru.kopyshev.rvs.UserTestData.*;
import static ru.kopyshev.rvs.UserTestData.USER_MATCHER;

public class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(USER);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void readWriteValues() {
        List<User> expected = Arrays.asList(USER, ADMIN);
        String json = JsonUtil.writeValue(expected);
        System.out.println(json);
        List<User> actual = JsonUtil.readValues(json, User.class);
        USER_MATCHER.assertMatch(actual, expected);
    }
}
