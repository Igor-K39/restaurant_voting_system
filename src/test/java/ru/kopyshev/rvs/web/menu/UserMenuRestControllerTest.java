package ru.kopyshev.rvs.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.testdata.MenuTestData.*;
import static ru.kopyshev.rvs.testdata.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.testdata.TestData.DATE_1;
import static ru.kopyshev.rvs.testdata.TestData.DATE_2;
import static ru.kopyshev.rvs.TestUtil.userHttpBasic;
import static ru.kopyshev.rvs.testdata.UserTestData.USER_AUTH;
import static ru.kopyshev.rvs.web.menu.UserMenuRestController.REST_URL;

class UserMenuRestControllerTest extends AbstractControllerTest {

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + MENU_ID_1)
                .with(userHttpBasic(USER_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MENU_DTO_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_AUTH))
                .param("start", DATE_1.toString())
                .param("end", DATE_2.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(MENU_DTO_4, MENU_DTO_2, MENU_DTO_3, MENU_DTO_1)));
    }

    @Test
    void getOfRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_AUTH))
                .param("restaurant", "" + RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(MENU_DTO_1, MENU_DTO_2)));
    }

    @Test
    void getOfBetweenDates() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_AUTH))
                .param("start", DATE_1.toString())
                .param("end", DATE_1.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(MENU_DTO_3, MENU_DTO_1)));
    }

    @Test
    void getOfBetweenDatesOfRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_AUTH))
                .param("start", DATE_1.toString())
                .param("end", DATE_1.toString())
                .param("restaurant", "" + RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(MENU_DTO_1)));
    }
}