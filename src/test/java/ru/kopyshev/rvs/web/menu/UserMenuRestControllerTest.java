package ru.kopyshev.rvs.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.service.MenuService;
import ru.kopyshev.rvs.util.MenuUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.MenuTestData.MENU_TO_MATCHER;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.TestData.DATE_1;

class UserMenuRestControllerTest extends AbstractControllerTest {
    private static final String restUrl = UserMenuRestController.REST_URL + "/";

    @Autowired
    private MenuService service;

    @Test
    void getAllWithRestaurant() throws Exception {
        var menuItems = service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1);
        var menuTosExpected = MenuUtil.getMenuTos(menuItems);

        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1 + "/menu")
                .param("date", DATE_1.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(menuTosExpected));
    }

    @Test
    void getAll() throws Exception {
        var menuItems = service.getAll(DATE_1, DATE_1);
        var menuTosExpected = MenuUtil.getMenuTos(menuItems);
        perform(MockMvcRequestBuilders.get(restUrl + "menu")
                .param("start", DATE_1.toString())
                .param("end", DATE_1.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(menuTosExpected));
    }
}