package ru.kopyshev.rvs.web.dish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.DishService;
import ru.kopyshev.rvs.to.DishTo;
import ru.kopyshev.rvs.util.DishUtil;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.DishTestData.*;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.UserTestData.ADMIN_AUTH;

class AdminDishRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = "/rest/admin/restaurants/";

    @Autowired
    private DishService service;

    @Test
    void createWithLocation() throws Exception {
        var dish = getNew();
        var json = JsonUtil.writeValue(dish);

        ResultActions actions = perform(MockMvcRequestBuilders.post(restUrl + RESTAURANT_ID_1 + "/dishes")
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());

        var createdTo = DISH_TO_MATCHER.readFromJson(actions);
        int createdId = createdTo.getId();
        var createdDish = service.get(createdId, RESTAURANT_ID_1);
        DISH_TO_MATCHER.assertMatch(DishUtil.getToFromDish(createdDish), createdTo);
    }

    @Test
    void get() throws Exception {
        var dishTo = DishUtil.getToFromDish(DISH_1);
        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1 + "/dishes/" + DISH_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(dishTo));
    }

    @Test
    void update() throws Exception {
        var dishTo = DishUtil.getToFromDish(DISH_1);
        dishTo.setName("new name");
        perform(MockMvcRequestBuilders.put(restUrl + RESTAURANT_ID_1 + "/dishes/" + DISH_ID_1)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DishTo actual = DishUtil.getToFromDish(service.get(DISH_ID_1, RESTAURANT_ID_1));
        DISH_TO_MATCHER.assertMatch(actual, dishTo);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(restUrl + RESTAURANT_ID_1 + "/dishes/" + DISH_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(DISH_ID_1, RESTAURANT_ID_1));
    }

    @Test
    void getAll() throws Exception {
        var dishes = service.getAll(RESTAURANT_ID_1);
        var tos = DishUtil.getToFromDish(dishes);
        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1 + "/dishes")
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(tos));
    }
}