package ru.kopyshev.rvs.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.service.UserService;
import ru.kopyshev.rvs.to.UserTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.kopyshev.rvs.util.CollectionUtil.addAllIfNotEmpty;
import static ru.kopyshev.rvs.util.UserUtil.getToFromUser;
import static ru.kopyshev.rvs.util.UserUtil.getUserFromTo;

public abstract class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    protected UserTo create(UserTo userTo) {
        log.info("create user from {}", userTo);
        User user = getUserFromTo(userTo);
        user = service.create(user);
        userTo.setId(user.id());
        return userTo;
    }

    protected UserTo get(int id) {
        log.info("get user {}", id);
        return getToFromUser(service.get(id));
    }

    protected List<UserTo> getAll() {
        log.info("get all users");
        return getToFromUser(service.getAll());
    }

    protected UserTo getByEmail(String email) {
        log.info("get user by email {}", email);
        return getToFromUser(service.getByEmail(email));
    }

    protected void update(Map<String, Object> updates, int userid, String... fields) {
        Map<String, Object> patch = new HashMap<>();
        addAllIfNotEmpty(updates, patch, fields);
        log.info("Update user with id {} by {}", userid, patch);
        service.update(patch, userid);
    }

    protected void delete(int id) {
        log.info("Delete user with id {}", id);
        service.delete(id);
    }
}