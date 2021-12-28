package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.to.RestaurantDTO;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant getRestaurantFromTo(RestaurantDTO to) {
        return new Restaurant(to.getId(), to.getName(), to.getAddress(), to.getWebsite());
    }

    public static RestaurantDTO getToFromRestaurant(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getWebsite());
    }

    public static List<RestaurantDTO> getToFromRestaurant(List<Restaurant> restaurants) {
        List<RestaurantDTO> tos = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            tos.add(getToFromRestaurant(restaurant));
        }
        return tos;
    }
}
