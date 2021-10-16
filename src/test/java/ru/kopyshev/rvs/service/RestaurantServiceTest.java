package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.Restaurant;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.TestData.NOT_FOUND_ID;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        Restaurant expected = getNew();
        Restaurant actual = service.create(expected);
        RESTAURANT_MATCHER.assertMatch(actual, expected);
        RESTAURANT_MATCHER.assertMatch(service.get(expected.id()), expected);
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID_1);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated(RESTAURANT_1);
        service.update(updated, RESTAURANT_ID_1);
        Restaurant actual = service.get(RESTAURANT_ID_1);
        RESTAURANT_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void delete() {
        Restaurant existing = service.get(RESTAURANT_ID_1);
        Assert.notNull(existing, "Must be an existing restaurant");
        service.delete(RESTAURANT_ID_1);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID_1));
    }

    @Test
    void getAll() {
        List<Restaurant> expected = List.of(RESTAURANT_1, RESTAURANT_2);
        List<Restaurant> actual = service.getAll();
        RESTAURANT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void createDuplicate() {
        Restaurant restaurant = new Restaurant(RESTAURANT_1);
        restaurant.setId(null);
        validateRootCause(ConstraintViolationException.class, () -> service.create(restaurant));
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant("   ", "Minsk", "restaurant.com")));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant("Restaurant", "   ", "restaurant.com")));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant("Restaurant", "Minsk", "   ")));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void updateNotConsistent() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(getUpdated(RESTAURANT_1), NOT_FOUND_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }
}