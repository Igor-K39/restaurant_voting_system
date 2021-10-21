package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.model.Dish;
import ru.kopyshev.rvs.model.MenuItem;
import ru.kopyshev.rvs.to.MenuTo;
import ru.kopyshev.rvs.to.NamedTo;

import java.time.LocalDate;
import java.util.List;

import static ru.kopyshev.rvs.DishTestData.*;
import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_1;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_2;
import static ru.kopyshev.rvs.TestData.DATE_1;
import static ru.kopyshev.rvs.TestData.DATE_2;
import static ru.kopyshev.rvs.util.RestaurantUtil.getToFromRestaurant;

@UtilityClass
public class MenuTestData {

    public static final Matcher<MenuItem> MENU_ITEM_MATCHER = usingIgnoreFieldComparator(MenuItem.class, "dish.restaurant");
    public static final Matcher<MenuTo> MENU_TO_MATCHER = usingIgnoreFieldComparator(MenuTo.class, "menuItemTos.id");
    public static final int ITEM_ID_1 = 100_010;
    public static final int ITEM_ID_2 = 100_011;
    public static final int ITEM_ID_3 = 100_012;
    public static final int ITEM_ID_4 = 100_013;
    public static final int ITEM_ID_5 = 100_014;
    public static final int ITEM_ID_6 = 100_015;
    public static final MenuItem ITEM_1 = new MenuItem(ITEM_ID_1, RESTAURANT_1, DISH_1, DATE_1, 13000);
    public static final MenuItem ITEM_2 = new MenuItem(ITEM_ID_2, RESTAURANT_1, DISH_2, DATE_1, 30000);
    public static final MenuItem ITEM_3 = new MenuItem(ITEM_ID_3, RESTAURANT_1, DISH_3, DATE_2, 32000);
    public static final MenuItem ITEM_4 = new MenuItem(ITEM_ID_4, RESTAURANT_2, DISH_4, DATE_1, 10000);
    public static final MenuItem ITEM_5 = new MenuItem(ITEM_ID_5, RESTAURANT_2, DISH_5, DATE_1, 17000);
    public static final MenuItem ITEM_6 = new MenuItem(ITEM_ID_6, RESTAURANT_2, DISH_6, DATE_2, 11000);

    public static MenuItem getNew() {
        return new MenuItem(RESTAURANT_1, DISH_1, LocalDate.now(), 100_000);
    }

    public static MenuItem getUpdated(MenuItem item, Dish dish) {
        var itemCopy = new MenuItem(item);
        var dateNextDay = LocalDate.now().plusDays(1);
        itemCopy.setDateOf(dateNextDay);
        itemCopy.setPrice(item.getPrice() * 2);
        itemCopy.setDish(dish);
        return itemCopy;
    }

    public static List<MenuItem> getAllUpdated(List<MenuItem> items) {
        var itemsCopy = List.copyOf(items);
        var newUpdated = items.get(0).getDateOf().plusDays(10);
        itemsCopy.forEach(item -> {
            item.setPrice(item.getPrice() * 2);
            item.setDateOf(newUpdated);
        });
        return itemsCopy;
    }

    public static MenuTo getMenuTo() {
        var dishTo1 = new NamedTo(DISH_1.id(), DISH_1.getName());
        var dishTo2 = new NamedTo(DISH_2.id(), DISH_2.getName());
        var menuItemTo1 = new MenuTo.MenuItemTo(ITEM_ID_1, dishTo1, ITEM_1.getPrice());
        var menuItemTo2 = new MenuTo.MenuItemTo(ITEM_ID_2, dishTo2, ITEM_2.getPrice());
        var menuItemTos = List.of(menuItemTo1, menuItemTo2);
        return new MenuTo(DATE_1, getToFromRestaurant(RESTAURANT_1), menuItemTos);
    }
}
