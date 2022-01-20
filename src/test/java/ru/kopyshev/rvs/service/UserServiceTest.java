package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.domain.Role;
import ru.kopyshev.rvs.dto.user.UserDTO;
import ru.kopyshev.rvs.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static ru.kopyshev.rvs.testdata.TestData.NOT_FOUND_ID;
import static ru.kopyshev.rvs.testdata.UserTestData.*;
import static ru.kopyshev.rvs.util.SecurityUtil.matches;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void create() {
        UserDTO expected = getNew();
        UserDTO created = service.create(expected);
        expected.setId(created.id());
        Assertions.assertTrue(matches(expected.getPassword(), created.getPassword()));
        USER_TO_MATCHER.assertMatch(created, expected);
        USER_TO_MATCHER.assertMatch(service.get(created.id()), created);
    }

    @Test
    void get() {
        UserDTO user = service.get(USER_ID);
        USER_TO_MATCHER.assertMatch(user, USER);
    }

    @Test
    void update() {
        UserDTO expected = getUpdated(USER);
        service.update(getMapWithUpdates(), USER_ID);
        UserDTO actual = service.get(expected.id());
        Assertions.assertTrue(matches(expected.getPassword(), actual.getPassword()));
        USER_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        UserDTO existing = service.get(USER_ID);
        Assert.notNull(existing, "Must be an existing user");
        service.delete(USER_ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void getAll() {
        List<UserDTO> actual = service.getAll();
        USER_TO_MATCHER.assertMatch(actual, List.of(ADMIN, USER));
    }

    @Test
    void getByEmail() {
        String email = USER.getEmail();
        UserDTO user = service.getByEmail(email);
        USER_TO_MATCHER.assertMatch(user, USER);
    }

    @Test
    void createDuplicate() {
        UserDTO user = new UserDTO(USER);
        user.setId(null);
        Assertions.assertThrows(DataAccessException.class, () -> service.create(user));
    }

    @Test
    void createWithException() {
        Set<Role> role = Set.of(Role.USER);
        validateRootCause(ConstraintViolationException.class, () -> service.create(new UserDTO(null, "  ", "mail@yandex.ru", "password", true, new Date(), role)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new UserDTO(null, "User", "  ", "password", true, new Date(), role)));
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
