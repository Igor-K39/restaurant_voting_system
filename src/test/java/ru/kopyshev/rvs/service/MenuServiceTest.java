package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.MenuTestData;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.MenuItem;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static ru.kopyshev.rvs.DishTestData.DISH_1;
import static ru.kopyshev.rvs.DishTestData.DISH_2;
import static ru.kopyshev.rvs.MenuTestData.getUpdated;
import static ru.kopyshev.rvs.MenuTestData.*;
import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.TestData.DATE_1;
import static ru.kopyshev.rvs.TestData.NOT_FOUND_ID;

public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void create() {
        MenuItem expected = MenuTestData.getNew();
        MenuItem actual = service.create(expected);
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
        MENU_ITEM_MATCHER.assertMatch(service.get(expected.id()), expected);
    }

    @Test
    void get() {
        MenuItem actual = service.get(ITEM_ID_1);
        MENU_ITEM_MATCHER.assertMatch(actual, ITEM_1);
    }

    @Test
    void update() {
        MenuItem expected = getUpdated(ITEM_1, DISH_2);
        service.update(expected, ITEM_ID_1);
        MenuItem actual = service.get(ITEM_ID_1);
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        service.delete(ITEM_ID_1);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(ITEM_ID_1));
    }

    @Test
    void createAll() {
        List<MenuItem> expected = List.of(new MenuItem(ITEM_1), new MenuItem(ITEM_2));
        expected.forEach(item -> {
            item.setId(null);
            item.setDateOf(LocalDate.now());
        });
        List<MenuItem> actual = service.createAll(expected);
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getAll() {
        List<MenuItem> expected = List.of(ITEM_6, ITEM_3, ITEM_5, ITEM_4, ITEM_2, ITEM_1);
        List<MenuItem> actual = service.getAll();
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getAllOfRestaurant() {
        List<MenuItem> expected = List.of(ITEM_3, ITEM_1, ITEM_2);
        List<MenuItem> actual = service.getAll(RESTAURANT_ID_1);
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getAllOfRestaurantAtDate() {
        List<MenuItem> expected = List.of(ITEM_1, ITEM_2);
        List<MenuItem> actual = service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1);
        System.out.println(DATE_1);
        System.out.println("Expected");
        expected.forEach(System.out::println);
        System.out.println("Actual");
        actual.forEach(System.out::println);
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getAllSortedByRestaurantAtDate() {
        List<MenuItem> expected = List.of(ITEM_5, ITEM_4, ITEM_2, ITEM_1);
        List<MenuItem> actual = service.getAll(DATE_1, DATE_1);
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void updateAllByReplace() {
        List<MenuItem> expected = service.updateAllByReplacing(getAllUpdated(service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1)));
        LocalDate dateOf = expected.get(0).getDateOf();
        List<MenuItem> actual = service.getAll(expected.get(0).getRestaurant().id(), dateOf, dateOf);
        for (int i = 0; i < expected.size(); i++) {
            actual.get(i).setId(expected.get(i).getId());
        }
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void deleteAtDate() {
        var existing = service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1);
        Assert.notEmpty(existing, "The collection of values to delete must not be empty");
        service.deleteAtDate(RESTAURANT_ID_1, DATE_1);
        var actual = service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1);
        Assertions.assertEquals(List.of(), actual);
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new MenuItem(null, DISH_1, LocalDate.now(), 1000)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new MenuItem(RESTAURANT_1, null, LocalDate.now(), 1000)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new MenuItem(RESTAURANT_1, DISH_1, null, 1000)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new MenuItem(RESTAURANT_1, DISH_1, LocalDate.now(), -1)));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void updateNotConsistent() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(getUpdated(ITEM_1, DISH_2), NOT_FOUND_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }
}
