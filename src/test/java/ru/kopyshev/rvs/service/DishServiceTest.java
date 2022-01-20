package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.testdata.DishTestData;
import ru.kopyshev.rvs.exception.IllegalRequestDataException;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.dto.dish.DishDTO;
import ru.kopyshev.rvs.dto.dish.DishUpdateDTO;
import ru.kopyshev.rvs.util.mapper.DishMapper;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;

import static ru.kopyshev.rvs.testdata.DishTestData.getUpdated;
import static ru.kopyshev.rvs.testdata.DishTestData.*;
import static ru.kopyshev.rvs.testdata.RestaurantTestData.*;
import static ru.kopyshev.rvs.testdata.TestData.NOT_FOUND_ID;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Autowired
    private DishMapper dishMapper;

    @Test
    void create() {
        DishDTO created = service.create(NEW_DISH_UPDATE_DTO);
        DISH_TO_MATCHER.assertMatch(created, NEW_DISH_DTO);
        DISH_TO_MATCHER.assertMatch(service.get(created.id()), created);
    }

    @Test
    void get() {
        DishDTO dishDTO = service.get(DISH_ID_1);
        DISH_TO_MATCHER.assertMatch(dishDTO, DISH_DTO_1);
    }

    @Test
    void update() {
        DishUpdateDTO updateDTO = DishTestData.getUpdated(DISH_DTO_1, RESTAURANT_ID_2);
        service.update(updateDTO, updateDTO.id());
        DishDTO actual = service.get(DISH_ID_1);
        DishDTO expected = new DishDTO();
        expected.setId(updateDTO.id());
        expected.setName(updateDTO.getName());
        expected.setRestaurant(RESTAURANT_TO_2);
        DISH_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        DishDTO existing = service.get(DISH_ID_1);
        Assert.notNull(existing, "Must be an existing restaurant");
        service.delete(DISH_ID_1);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(DISH_ID_1));
    }

    @Test
    void getAll() {
        List<DishDTO> expected = List.of(DISH_DTO_6, DISH_DTO_5, DISH_DTO_4);
        List<DishDTO> actual = service.getAll(RESTAURANT_ID_2);
        DISH_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void createDuplicate() {
        DishUpdateDTO updateDTO = new DishUpdateDTO();
        updateDTO.setId(null);
        updateDTO.setName(DISH_DTO_1.getName());
        updateDTO.setRestaurantId(DISH_DTO_1.getRestaurant().id());
        validateRootCause(SQLException.class, () -> service.create(updateDTO));
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(new DishUpdateDTO(null, "   ", RESTAURANT_ID_1)));
        validateRootCause(IllegalArgumentException.class,
                () -> service.create(new DishUpdateDTO(null, "The valid name of a dish", null)));
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void updateNotConsistent() {
        DishUpdateDTO updateDTO = getUpdated(DISH_DTO_1, RESTAURANT_ID_2);
        Assertions.assertThrows(IllegalRequestDataException.class, () ->
                service.update(updateDTO, NOT_FOUND_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }
}
