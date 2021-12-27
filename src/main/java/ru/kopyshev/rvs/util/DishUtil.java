package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.Dish;
import ru.kopyshev.rvs.to.DishTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DishUtil {

    public static Dish getDishFromTo(DishTo dishTo) {
        var restaurant = RestaurantUtil.getRestaurantFromTo(dishTo.getRestaurant());
        return new Dish(dishTo.getId(), dishTo.getName(), restaurant);
    }

    public static List<Dish> getDishFromTo(List<DishTo> dishTos) {
        return dishTos.stream().map(DishUtil::getDishFromTo).collect(Collectors.toList());
    }

    public static DishTo getToFromDish(Dish dish) {
        var restaurantTo = RestaurantUtil.getToFromRestaurant(dish.getRestaurant());
        return new DishTo(dish.getId(), dish.getName(), restaurantTo);
    }

    public static List<DishTo> getToFromDish(List<Dish> dishes) {
        return dishes.stream().map(DishUtil::getToFromDish).collect(Collectors.toList());
    }
}
