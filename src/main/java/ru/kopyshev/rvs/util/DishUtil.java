package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.Dish;
import ru.kopyshev.rvs.to.DishDTO;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DishUtil {

    public static Dish getDishFromTo(DishDTO dishTo) {
        var restaurant = RestaurantUtil.getRestaurantFromTo(dishTo.getRestaurant());
        return new Dish(dishTo.getId(), dishTo.getName(), restaurant);
    }

    public static List<Dish> getDishFromTo(List<DishDTO> dishTos) {
        return dishTos.stream().map(DishUtil::getDishFromTo).collect(Collectors.toList());
    }

    public static DishDTO getToFromDish(Dish dish) {
        var restaurantTo = RestaurantUtil.getToFromRestaurant(dish.getRestaurant());
        return new DishDTO(dish.getId(), dish.getName(), restaurantTo);
    }

    public static List<DishDTO> getToFromDish(List<Dish> dishes) {
        return dishes.stream().map(DishUtil::getToFromDish).collect(Collectors.toList());
    }
}
