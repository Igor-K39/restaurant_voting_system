package ru.kopyshev.rvs.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.dto.user.UserDTO;
import ru.kopyshev.rvs.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.kopyshev.rvs.util.CollectionUtil.addAllIfNotEmpty;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    protected UserDTO create(UserDTO userDTO) {
        log.info("create user from {}", userDTO);
        return service.create(userDTO);
    }

    protected UserDTO get(int id) {
        log.info("get user {}", id);
        return service.get(id);
    }

    protected List<UserDTO> getAll() {
        log.info("get all users");
        return service.getAll();
    }

    protected UserDTO getByEmail(String email) {
        log.info("get user by email {}", email);
        return service.getByEmail(email);
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