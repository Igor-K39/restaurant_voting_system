package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.DishTestData;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.Dish;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;

import static ru.kopyshev.rvs.DishTestData.*;
import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.TestData.NOT_FOUND_ID;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    void create() {
        Dish expected = DishTestData.getNew();
        Dish created = service.create(expected);
        DISH_MATCHER.assertMatch(created, expected);
        DISH_MATCHER.assertMatch(service.get(created.id(), expected.getRestaurant().id()), created);
    }

    @Test
    void get() {
        Dish dish = service.get(DISH_ID_1, RESTAURANT_ID_1);
        DISH_MATCHER.assertMatch(dish, DISH_1);
    }

    @Test
    void update() {
        Dish updated = DishTestData.getUpdated(DISH_1);
        service.update(updated, DISH_ID_1, RESTAURANT_ID_2);
        Dish actual = service.get(DISH_ID_1, RESTAURANT_ID_2);
        DISH_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void delete() {
        Dish existing = service.get(DISH_ID_1, RESTAURANT_ID_1);
        Assert.notNull(existing, "Must be an existing restaurant");
        service.delete(DISH_ID_1, RESTAURANT_ID_1);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(DISH_ID_1, RESTAURANT_ID_1));
    }

    @Test
    void getAll() {
        List<Dish> expected = List.of(DISH_6, DISH_5, DISH_4);
        List<Dish> actual = service.getAll(RESTAURANT_ID_2);
        DISH_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void createDuplicate() {
        Dish dish = new Dish(DISH_1);
        dish.setId(null);
        validateRootCause(SQLException.class, () -> service.create(dish));
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("   ", RESTAURANT_1)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("The valid name of a dish", null)));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID, RESTAURANT_ID_1));
    }

    @Test
    void updateNotConsistent() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.update(DishTestData.getUpdated(DISH_1), DISH_ID_1, NOT_FOUND_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID, RESTAURANT_ID_1));
    }
}
