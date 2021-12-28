package ru.kopyshev.rvs.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.service.UserService;
import ru.kopyshev.rvs.to.UserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.kopyshev.rvs.util.CollectionUtil.addAllIfNotEmpty;
import static ru.kopyshev.rvs.util.UserUtil.getToFromUser;
import static ru.kopyshev.rvs.util.UserUtil.getUserFromTo;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    protected UserDTO create(UserDTO userTo) {
        log.info("create user from {}", userTo);
        User user = getUserFromTo(userTo);
        user = service.create(user);
        userTo.setId(user.id());
        return userTo;
    }

    protected UserDTO get(int id) {
        log.info("get user {}", id);
        return getToFromUser(service.get(id));
    }

    protected List<UserDTO> getAll() {
        log.info("get all users");
        return getToFromUser(service.getAll());
    }

    protected UserDTO getByEmail(String email) {
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