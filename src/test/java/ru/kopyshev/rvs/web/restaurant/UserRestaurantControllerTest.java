package ru.kopyshev.rvs.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.util.RestaurantUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_TO_MATCHER;

public class UserRestaurantControllerTest extends AbstractControllerTest {
    private static final String restUrl = UserRestaurantController.REST_URL + "/";

    @Test
    void get() throws Exception {
        var restaurantTo = RestaurantUtil.getToFromRestaurant(RESTAURANT_1);
        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantTo));
    }

    @Test
    void getAll() throws Exception {
        var allRestaurantTo = RestaurantUtil.getToFromRestaurant(List.of(RESTAURANT_1, RESTAURANT_2));
        perform(MockMvcRequestBuilders.get(restUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(allRestaurantTo));
    }
}
