package ru.kopyshev.rvs.util;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.kopyshev.rvs.MenuTestData;
import ru.kopyshev.rvs.config.SpringApplicationConfigTest;
import ru.kopyshev.rvs.config.SpringDataJpaConfig;

import java.util.List;

import static ru.kopyshev.rvs.MenuTestData.*;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_1;
import static ru.kopyshev.rvs.TestData.DATE_1;

@SpringJUnitConfig(value = {SpringDataJpaConfig.class, SpringApplicationConfigTest.class})
public class MenuUtilTest {

    @Test
    void getMenuItems() {
        var menuTo = MenuTestData.getMenuTo();
        var expected = List.of(ITEM_1, ITEM_2);
        var actual = MenuUtil.getMenuItems(menuTo);
        MENU_ITEM_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getMenuTo() {
        var items = List.of(ITEM_1, ITEM_2);
        var expected = MenuTestData.getMenuTo();
        var actual = MenuUtil.getMenuTo(RESTAURANT_1, DATE_1, items);
        MENU_TO_MATCHER.assertMatch(actual, expected);
    }
}
