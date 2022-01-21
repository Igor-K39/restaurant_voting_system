package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.domain.Role;
import ru.kopyshev.rvs.domain.User;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static ru.kopyshev.rvs.TestData.NOT_FOUND_ID;
import static ru.kopyshev.rvs.UserTestData.*;
import static ru.kopyshev.rvs.UserTestData.getMapWithUpdates;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void create() {
        User expected = getNew();
        User created = service.create(expected);
        int userId = created.id();

        expected.setId(userId);
        USER_MATCHER.assertMatch(created, expected);
        USER_MATCHER.assertMatch(service.get(userId), created);
    }

    @Test
    void get() {
        User user = service.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void update() {
        User updated = getUpdated(USER);
        service.update(getMapWithUpdates(), USER_ID);
        User actual = service.get(USER_ID);
        USER_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void delete() {
        User existing = service.get(USER_ID);
        Assert.notNull(existing, "Must be an existing user");
        service.delete(USER_ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void getAll() {
        List<User> actual = service.getAll();
        USER_MATCHER.assertMatch(actual, List.of(ADMIN, USER));
    }

    @Test
    void getByEmail() {
        String email = USER.getEmail();
        User user = service.getByEmail(email);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void createDuplicate() {
        User user = new User(USER);
        user.setId(null);
        Assertions.assertThrows(DataAccessException.class, () -> service.create(user));
    }

    @Test
    void createWithException() {
        Set<Role> role = Set.of(Role.USER);
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "  ", "mail@yandex.ru", "password", role)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "  ", "password", role)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "  ", role)));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }
}
