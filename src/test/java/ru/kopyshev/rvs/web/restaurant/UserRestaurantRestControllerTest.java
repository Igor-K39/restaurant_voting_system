package ru.kopyshev.rvs.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.dto.RestaurantDTO;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.testdata.RestaurantTestData.*;

public class UserRestaurantRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = UserRestaurantRestController.REST_URL + "/";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANT_TO_1));
    }

    @Test
    void getAll() throws Exception {
        List<RestaurantDTO> restaurantDTOs = List.of(RESTAURANT_TO_1, RESTAURANT_TO_2);
        perform(MockMvcRequestBuilders.get(restUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantDTOs));
    }
}
