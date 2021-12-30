package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.to.RestaurantDTO;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;

@UtilityClass
public class RestaurantTestData {

    public static final Matcher<RestaurantDTO> RESTAURANT_TO_MATCHER = usingIgnoreFieldComparator(RestaurantDTO.class, "id");
    public static final int RESTAURANT_ID_1 = 100_002;
    public static final int RESTAURANT_ID_2 = 100_003;

    public static final Restaurant RESTAURANT_1 =
            new Restaurant(RESTAURANT_ID_1, "Restaurant", "Minsk", "restaurant.com");
    public static final Restaurant RESTAURANT_2 =
            new Restaurant(RESTAURANT_ID_2, "The restaurant 2", "Minsk", "WannaBeRestaurant.com");

    public static final RestaurantDTO RESTAURANT_TO_1 =
            new RestaurantDTO(RESTAURANT_ID_1, "Restaurant", "Minsk", "restaurant.com");
    public static final RestaurantDTO RESTAURANT_TO_2 =
            new RestaurantDTO(RESTAURANT_ID_2, "The restaurant 2", "Minsk", "WannaBeRestaurant.com");

    public static final RestaurantDTO NEW_RESTAURANT_DTO =
            new RestaurantDTO("New Restaurant!", "New Address", "New Website");

    public static RestaurantDTO getUpdated(RestaurantDTO restaurantDTO) {
        RestaurantDTO updatedDTO = new RestaurantDTO(restaurantDTO);
        updatedDTO.setName(updatedDTO.getName() + " updated");
        updatedDTO.setAddress(updatedDTO.getAddress() + " updated");
        updatedDTO.setWebsite(updatedDTO.getWebsite() + " updated");
        return updatedDTO;
    }
}