package ru.kopyshev.rvs.web.menu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kopyshev.rvs.model.MenuItem;
import ru.kopyshev.rvs.service.MenuService;
import ru.kopyshev.rvs.to.MenuTo;
import ru.kopyshev.rvs.to.NamedTo;
import ru.kopyshev.rvs.util.JsonUtil;
import ru.kopyshev.rvs.util.MenuUtil;
import ru.kopyshev.rvs.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kopyshev.rvs.DishTestData.*;
import static ru.kopyshev.rvs.MenuTestData.MENU_TO_MATCHER;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_1;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.TestData.DATE_1;
import static ru.kopyshev.rvs.TestData.DATE_2;
import static ru.kopyshev.rvs.util.RestaurantUtil.getToFromRestaurant;

class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String restUrl = AdminMenuController.REST_URL;

    @Autowired
    private MenuService service;

    @Test
    void createWithLocation() throws Exception {
        var menuItemTo1 = new MenuTo.MenuItemTo(new NamedTo(DISH_1.getId(), DISH_1.getName()), 10000);
        var menuItemTo2 = new MenuTo.MenuItemTo(new NamedTo(DISH_2.getId(), DISH_2.getName()), 20000);
        var menuItemTo3 = new MenuTo.MenuItemTo(new NamedTo(DISH_3.getId(), DISH_3.getName()), 30000);
        var menuItemTos = List.of(menuItemTo1, menuItemTo2, menuItemTo3);
        var menuTo = new MenuTo();
        menuTo.setDateOf(LocalDate.now());
        menuTo.setRestaurantTo(getToFromRestaurant(RESTAURANT_1));
        menuTo.setMenuItemTos(menuItemTos);
        System.out.println(menuTo);

        perform(MockMvcRequestBuilders.post(restUrl + "menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        MenuTo actual = MenuUtil.getMenuTo(RESTAURANT_1, LocalDate.now(),
                service.getAll(RESTAURANT_ID_1, LocalDate.now(), LocalDate.now()));
        MENU_TO_MATCHER.assertMatch(actual, menuTo);
    }

    @Test
    void getAllWithRestaurant() throws Exception {
        var menuTos = MenuUtil.getMenuTos(service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1));

        perform(MockMvcRequestBuilders.get(restUrl + RESTAURANT_ID_1 + "/menu")
                .param("start", DATE_1.toString())
                .param("end", DATE_1.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(menuTos));
    }

    @Test
    void getAll() throws Exception {
        var menuTos = MenuUtil.getMenuTos(service.getAll(DATE_1, DATE_2));
        menuTos.forEach(System.out::println);
        perform(MockMvcRequestBuilders.get(restUrl + "/menu")
                .param("start", DATE_1.toString())
                .param("end", DATE_2.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(menuTos));

    }

    @Test
    void update() throws Exception {
        var menuItems = service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1);
        menuItems.forEach(System.out::println);
        menuItems.forEach(item -> item.setPrice(item.getPrice() * 2));
        menuItems.add(new MenuItem(RESTAURANT_1, DISH_3, DATE_1, 100_000));
        menuItems.forEach(System.out::println);
        var menuTo = MenuUtil.getMenuTo(RESTAURANT_1, DATE_1, menuItems);

        perform(MockMvcRequestBuilders.put(restUrl + "menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1).forEach(System.out::println);
        MenuTo actual = MenuUtil.getMenuTo(RESTAURANT_1, DATE_1, service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1));
        MENU_TO_MATCHER.assertMatch(menuTo, actual);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(restUrl + RESTAURANT_ID_1+ "/menu/" + DATE_1))
                .andDo(print())
                .andExpect(status().isNoContent());
        System.out.println("ALL");
        Assertions.assertEquals(List.of(), service.getAll(RESTAURANT_ID_1, DATE_1, DATE_1));
    }
}