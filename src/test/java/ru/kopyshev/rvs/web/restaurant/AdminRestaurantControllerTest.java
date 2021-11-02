package ru.kopyshev.rvs.web.restaurant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.RestaurantTestData;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.RestaurantService;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.util.RestaurantUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.UserTestData.ADMIN;

class AdminRestaurantControllerTest extends AbstractControllerTest {
    private static final String restUrl = AdminRestaurantController.ADMIN_REST_URL + "/";

    @Autowired
    private RestaurantService service;

    @Test
    void createWithLocation() throws Exception {
        var restaurantTo = RestaurantUtil.getToFromRestaurant(RestaurantTestData.getNew());
        ResultActions actions = perform(MockMvcRequestBuilders.post(restUrl)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        var createdTo = RESTAURANT_TO_MATCHER.readFromJson(actions);
        int id = createdTo.getId();
        restaurantTo.setId(id);
        RESTAURANT_TO_MATCHER.assertMatch(createdTo, restaurantTo);
        RESTAURANT_MATCHER.assertMatch(service.get(id), RestaurantUtil.getRestaurantFromTo(restaurantTo));
    }

    @Test
    void get() throws Exception {
        var restaurantTo = RestaurantUtil.getToFromRestaurant(RESTAURANT_1);
        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantTo));
    }

    @Test
    void getAll() throws Exception {
        var allRestaurantTo = RestaurantUtil.getToFromRestaurant(List.of(RESTAURANT_1, RESTAURANT_2));
        perform(MockMvcRequestBuilders.get(restUrl)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(allRestaurantTo));
    }

    @Test
    void update() throws Exception {
        var restaurantTo = RestaurantUtil.getToFromRestaurant(getUpdated(RESTAURANT_1));
        perform(MockMvcRequestBuilders.put(restUrl + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        var actual = RestaurantUtil.getToFromRestaurant(service.get(RESTAURANT_ID_1));
        RESTAURANT_TO_MATCHER.assertMatch(actual, restaurantTo);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(restUrl + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID_1));
    }
}