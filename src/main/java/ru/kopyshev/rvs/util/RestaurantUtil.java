package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.to.RestaurantTo;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant getRestaurantFromTo(RestaurantTo to) {
        return new Restaurant(to.getId(), to.getName(), to.getAddress(), to.getWebsite());
    }

    public static RestaurantTo getToFromRestaurant(Restaurant restaurant) {
        return new RestaurantTo(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getWebsite());
    }

    public static List<RestaurantTo> getToFromRestaurant(List<Restaurant> restaurants) {
        List<RestaurantTo> tos = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            tos.add(getToFromRestaurant(restaurant));
        }
        return tos;
    }
}
