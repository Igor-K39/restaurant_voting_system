package ru.kopyshev.rvs.web.restaurant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.RestaurantService;
import ru.kopyshev.rvs.to.RestaurantDTO;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.RestaurantTestData.*;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.UserTestData.ADMIN_AUTH;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = AdminRestaurantRestController.ADMIN_REST_URL + "/";

    @Autowired
    private RestaurantService service;

    @Test
    void createWithLocation() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.post(restUrl)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(NEW_RESTAURANT_DTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        RestaurantDTO createdDTO = RESTAURANT_TO_MATCHER.readFromJson(actions);
        RESTAURANT_TO_MATCHER.assertMatch(createdDTO, NEW_RESTAURANT_DTO);
        RESTAURANT_TO_MATCHER.assertMatch(service.get(createdDTO.getId()), NEW_RESTAURANT_DTO);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANT_TO_1));
    }

    @Test
    void getAll() throws Exception {
        List<RestaurantDTO> restaurantDTOs = List.of(RESTAURANT_TO_1, RESTAURANT_TO_2);
        perform(MockMvcRequestBuilders.get(restUrl)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantDTOs));
    }

    @Test
    void update() throws Exception {
        RestaurantDTO expected = getUpdated(RESTAURANT_TO_1);
        perform(MockMvcRequestBuilders.put(restUrl + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RestaurantDTO actual = service.get(RESTAURANT_ID_1);
        RESTAURANT_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(restUrl + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assertions.assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID_1));
    }
}