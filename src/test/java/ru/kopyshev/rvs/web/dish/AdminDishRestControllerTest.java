package ru.kopyshev.rvs.web.dish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.dto.dish.DishDTO;
import ru.kopyshev.rvs.dto.dish.DishUpdateDTO;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.DishService;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.testdata.DishTestData.getUpdated;
import static ru.kopyshev.rvs.testdata.DishTestData.*;
import static ru.kopyshev.rvs.testdata.RestaurantTestData.*;
import static ru.kopyshev.rvs.testdata.UserTestData.ADMIN_AUTH;

class AdminDishRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = "/rest/admin/dishes/";

    @Autowired
    private DishService service;

    @Test
    void createWithLocation() throws Exception {
        String json = JsonUtil.writeValue(NEW_DISH_UPDATE_DTO);

        ResultActions actions = perform(MockMvcRequestBuilders.post(restUrl)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());

        DishDTO expectedDTO = DISH_TO_MATCHER.readFromJson(actions);
        DishDTO actualDTO = service.get(expectedDTO.id());
        DISH_TO_MATCHER.assertMatch(actualDTO, expectedDTO);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(restUrl + DISH_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DISH_DTO_1));
    }

    @Test
    void update() throws Exception {
        DishUpdateDTO updateDTO = getUpdated(DISH_DTO_1, RESTAURANT_ID_2);
        DishDTO expected = new DishDTO(DISH_DTO_1);
        expected.setName(updateDTO.getName());
        expected.setRestaurant(RESTAURANT_TO_2);

        perform(MockMvcRequestBuilders.put(restUrl + DISH_ID_1)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateDTO)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DishDTO actual = service.get(DISH_ID_1);
        DISH_TO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(restUrl + DISH_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(DISH_ID_1));
    }

    @Test
    void getAll() throws Exception {
        List<DishDTO> expected = service.getAll(RESTAURANT_ID_1);
        perform(MockMvcRequestBuilders.get(restUrl).param("restaurant", String.valueOf(RESTAURANT_ID_1))
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(expected));
    }
}