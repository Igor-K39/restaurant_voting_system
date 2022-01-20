package ru.kopyshev.rvs.web.menu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.testdata.MenuTestData;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.service.MenuService;
import ru.kopyshev.rvs.dto.menu.MenuDTO;
import ru.kopyshev.rvs.dto.menu.MenuUpdateDTO;
import ru.kopyshev.rvs.util.JsonUtil;
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
import static ru.kopyshev.rvs.testdata.UserTestData.ADMIN_AUTH;
import static ru.kopyshev.rvs.web.menu.AdminMenuRestController.REST_URL;

class AdminMenuRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuService service;

    @Test
    void createWithLocation() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.NEW_MENU_UPDATE_DTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        MenuDTO actual = MENU_TO_MATCHER.readFromJson(actions);
        MENU_TO_MATCHER.assertMatch(actual, service.get(actual.id()));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + MENU_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MENU_DTO_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN_AUTH))
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
                .with(userHttpBasic(ADMIN_AUTH))
                .param("restaurant", "" + RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(MENU_DTO_1, MENU_DTO_2)));
    }

    @Test
    void getOfBetweenDates() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN_AUTH))
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
                .with(userHttpBasic(ADMIN_AUTH))
                .param("start", DATE_1.toString())
                .param("end", DATE_1.toString())
                .param("restaurant", "" + RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(MENU_DTO_1)));
    }

    @Test
    void update() throws Exception {
        MenuUpdateDTO menuUpdateDTO = new MenuUpdateDTO(UPDATED_MENU_UPDATE_DTO);
        menuUpdateDTO.setId(MENU_ID_1);
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU_ID_1)
                .with(userHttpBasic(ADMIN_AUTH))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuUpdateDTO)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER.assertMatch(service.get(MENU_ID_1), UPDATED_MENU_DTO);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + MENU_ID_1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(MENU_ID_1));
    }
}