package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.model.Dish;
import ru.kopyshev.rvs.to.DishTo;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_1;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_2;

@UtilityClass
public class DishTestData {

    public static final Matcher<Dish> DISH_MATCHER = usingIgnoreFieldComparator(Dish.class, "restaurant");
    public static final Matcher<DishTo> DISH_TO_MATCHER = usingIgnoreFieldComparator(DishTo.class);
    public static final int DISH_ID_1 = 100_004;
    public static final int DISH_ID_2 = 100_005;
    public static final int DISH_ID_3 = 100_006;
    public static final int DISH_ID_4 = 100_007;
    public static final int DISH_ID_5 = 100_008;
    public static final int DISH_ID_6 = 100_009;
    public static final Dish DISH_1 = new Dish(DISH_ID_1, "R1-Borsch", RESTAURANT_1);
    public static final Dish DISH_2 = new Dish(DISH_ID_2, "R1-Beef", RESTAURANT_1);
    public static final Dish DISH_3 = new Dish(DISH_ID_3, "R1-Pork", RESTAURANT_1);
    public static final Dish DISH_4 = new Dish(DISH_ID_4, "R2-Potato", RESTAURANT_2);
    public static final Dish DISH_5 = new Dish(DISH_ID_5, "R2-Mushrooms", RESTAURANT_2);
    public static final Dish DISH_6 = new Dish(DISH_ID_6, "R2-Cabbage", RESTAURANT_2);

    public static final String DISH_UPDATED_NAME = "Updated BORSCH";

    public Dish getNew() {
        Dish aNew = new Dish();
        aNew.setName("New BORSCH");
        aNew.setRestaurant(RESTAURANT_1);
        return aNew;
    }

    public Dish getUpdated(Dish dish) {
        Dish updated = new Dish(dish);
        updated.setName(DISH_UPDATED_NAME);
        updated.setRestaurant(RESTAURANT_2);
        return updated;
    }
}