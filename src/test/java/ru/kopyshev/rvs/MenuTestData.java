package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.dto.NamedDTO;
import ru.kopyshev.rvs.dto.menu.MenuDTO;
import ru.kopyshev.rvs.dto.menu.MenuDTO.MenuItemDTO;
import ru.kopyshev.rvs.dto.menu.MenuUpdateDTO;
import ru.kopyshev.rvs.dto.menu.MenuUpdateDTO.MenuItemUpdateDTO;

import java.time.LocalDate;
import java.util.List;

import static ru.kopyshev.rvs.DishTestData.*;
import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;
import static ru.kopyshev.rvs.RestaurantTestData.*;

@UtilityClass
public class MenuTestData {

    public static final Matcher<MenuDTO> MENU_TO_MATCHER = usingIgnoreFieldComparator(MenuDTO.class, "id", "menuItems.id");
    public static final int MENU_ID_1 = 100_010;
    public static final int MENU_ID_2 = 100_011;
    public static final int MENU_ID_3 = 100_012;
    public static final int MENU_ID_4 = 100_013;

    public static final int MENU_ITEM_ID_1 = 100_014;
    public static final int MENU_ITEM_ID_2 = 100_015;
    public static final int MENU_ITEM_ID_3 = 100_016;
    public static final int MENU_ITEM_ID_4 = 100_017;
    public static final int MENU_ITEM_ID_5 = 100_018;
    public static final int MENU_ITEM_ID_6 = 100_019;
    public static final int MENU_ITEM_ID_7 = 100_020;
    public static final int MENU_ITEM_ID_8 = 100_021;

    public static final LocalDate MENU_DATE_1 = LocalDate.of(2021, 3, 1);
    public static final LocalDate MENU_DATE_2 = LocalDate.of(2021, 3, 2);

    public static final MenuItemDTO MENU_ITEM_DTO_1 = new MenuItemDTO(MENU_ITEM_ID_1, new NamedDTO(DISH_ID_1, "R1-Borsch"), 13000);
    public static final MenuItemDTO MENU_ITEM_DTO_2 = new MenuItemDTO(MENU_ITEM_ID_2, new NamedDTO(DISH_ID_2, "R1-Beef"), 30000);
    public static final MenuItemDTO MENU_ITEM_DTO_3 = new MenuItemDTO(MENU_ITEM_ID_3, new NamedDTO(DISH_ID_2, "R1-Beef"), 32000);
    public static final MenuItemDTO MENU_ITEM_DTO_4 = new MenuItemDTO(MENU_ITEM_ID_4, new NamedDTO(DISH_ID_3, "R1-Pork"), 19000);
    public static final MenuItemDTO MENU_ITEM_DTO_5 = new MenuItemDTO(MENU_ITEM_ID_5, new NamedDTO(DISH_ID_4, "R2-Potato"), 10000);
    public static final MenuItemDTO MENU_ITEM_DTO_6 = new MenuItemDTO(MENU_ITEM_ID_6, new NamedDTO(DISH_ID_5, "R2-Mushrooms"), 12000);
    public static final MenuItemDTO MENU_ITEM_DTO_7 = new MenuItemDTO(MENU_ITEM_ID_7, new NamedDTO(DISH_ID_5, "R2-Mushrooms"), 17000);
    public static final MenuItemDTO MENU_ITEM_DTO_8 = new MenuItemDTO(MENU_ITEM_ID_8, new NamedDTO(DISH_ID_6, "R2-Cabbage"), 11000);

    public static final MenuDTO MENU_DTO_1 =
            new MenuDTO(MENU_ID_1, "Меню 1", RESTAURANT_TO_1, MENU_DATE_1, List.of(MENU_ITEM_DTO_1, MENU_ITEM_DTO_2));
    public static final MenuDTO MENU_DTO_2 =
            new MenuDTO(MENU_ID_2, "Меню 2", RESTAURANT_TO_1, MENU_DATE_2, List.of(MENU_ITEM_DTO_3, MENU_ITEM_DTO_4));
    public static final MenuDTO MENU_DTO_3 =
            new MenuDTO(MENU_ID_3, "Меню 3", RESTAURANT_TO_2, MENU_DATE_1, List.of(MENU_ITEM_DTO_5, MENU_ITEM_DTO_6));
    public static final MenuDTO MENU_DTO_4 =
            new MenuDTO(MENU_ID_4, "Меню 4", RESTAURANT_TO_2, MENU_DATE_2, List.of(MENU_ITEM_DTO_7, MENU_ITEM_DTO_8));

    public static final int NEW_PRICE_1 = 10000;
    public static final int NEW_PRICE_2 = 20000;
    public static final int UPDATED_PRICE_1 = NEW_PRICE_1 * 2;
    public static final int UPDATED_PRICE_2 = NEW_PRICE_2 * 2;
    public static final String NEW_MENU_NAME = "New menu";
    public static final String UPDATED_MENU_NAME = "Updated menu name";


    public static final MenuUpdateDTO NEW_MENU_UPDATE_DTO =
            new MenuUpdateDTO(NEW_MENU_NAME, RESTAURANT_ID_1, LocalDate.now(), List.of(
                    new MenuItemUpdateDTO(DISH_ID_1, NEW_PRICE_1),
                    new MenuItemUpdateDTO(DISH_ID_2, NEW_PRICE_2))
            );

    public static final MenuDTO NEW_MENU_DTO =
            new MenuDTO(NEW_MENU_NAME, RESTAURANT_TO_1, LocalDate.now(), List.of(
                    new MenuItemDTO(DISH_DTO_1, NEW_PRICE_1),
                    new MenuItemDTO(DISH_DTO_2, NEW_PRICE_2))
            );

    public static final MenuUpdateDTO UPDATED_MENU_UPDATE_DTO =
            new MenuUpdateDTO(UPDATED_MENU_NAME, RESTAURANT_ID_2, LocalDate.now().plusDays(1), List.of(
                    new MenuItemUpdateDTO(DISH_ID_2, UPDATED_PRICE_1),
                    new MenuItemUpdateDTO(DISH_ID_3, UPDATED_PRICE_2))
            );

    public static final MenuDTO UPDATED_MENU_DTO =
            new MenuDTO(UPDATED_MENU_NAME, RESTAURANT_TO_2, LocalDate.now().plusDays(1), List.of(
                    new MenuItemDTO(DISH_DTO_2, UPDATED_PRICE_1),
                    new MenuItemDTO(DISH_DTO_3, UPDATED_PRICE_2)
            ));
}
