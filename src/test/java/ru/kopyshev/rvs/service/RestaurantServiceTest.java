package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.exception.IllegalRequestDataException;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.dto.RestaurantDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.TestData.NOT_FOUND_ID;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        RestaurantDTO actual = service.create(NEW_RESTAURANT_DTO);
        RESTAURANT_TO_MATCHER.assertMatch(actual, NEW_RESTAURANT_DTO);
        RESTAURANT_TO_MATCHER.assertMatch(service.get(actual.id()), NEW_RESTAURANT_DTO);
    }

    @Test
    void get() {
        RestaurantDTO restaurant = service.get(RESTAURANT_ID_1);
        RESTAURANT_TO_MATCHER.assertMatch(restaurant, RESTAURANT_TO_1);
    }

    @Test
    void update() {
        RestaurantDTO updated = getUpdated(RESTAURANT_TO_1);
        service.update(updated, RESTAURANT_ID_1);
        RestaurantDTO actual = service.get(RESTAURANT_ID_1);
        RESTAURANT_TO_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void delete() {
        RestaurantDTO existing = service.get(RESTAURANT_ID_1);
        Assert.notNull(existing, "Must be an existing restaurant");
        service.delete(RESTAURANT_ID_1);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID_1));
    }

    @Test
    void getAll() {
        List<RestaurantDTO> expected = List.of(RESTAURANT_TO_1, RESTAURANT_TO_2);
        List<RestaurantDTO> actual = service.getAll();
        RESTAURANT_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void createDuplicate() {
        RestaurantDTO restaurant = new RestaurantDTO(RESTAURANT_TO_1);
        restaurant.setId(null);
        validateRootCause(ConstraintViolationException.class, () -> service.create(restaurant));
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RestaurantDTO("   ", "Minsk", "restaurant.com")));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RestaurantDTO("Restaurant", "   ", "restaurant.com")));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RestaurantDTO("Restaurant", "Minsk", "   ")));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void updateNotConsistent() {
        Assertions.assertThrows(IllegalRequestDataException.class, () -> service.update(getUpdated(RESTAURANT_TO_1), NOT_FOUND_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }
}