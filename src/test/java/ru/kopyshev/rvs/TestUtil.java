package ru.kopyshev.rvs;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.kopyshev.rvs.model.User;

public class TestUtil {
    public static RequestPostProcessor userHttpBasic(User user) {
        String prefix = "{noop}";
        String password = user.getPassword().startsWith(prefix)
                ? user.getPassword().substring(prefix.length())
                : user.getPassword();
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), password);
    }
}
