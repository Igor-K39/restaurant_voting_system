package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.to.RestaurantDTO;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;

@UtilityClass
public class RestaurantTestData {

    public static final Matcher<Restaurant> RESTAURANT_MATCHER = usingIgnoreFieldComparator(Restaurant.class);
    public static final Matcher<RestaurantDTO> RESTAURANT_TO_MATCHER = usingIgnoreFieldComparator(RestaurantDTO.class);
    public static final int RESTAURANT_ID_1 = 100_002;
    public static final int RESTAURANT_ID_2 = 100_003;
    public static final
    Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID_1, "Restaurant", "Minsk", "restaurant.com");
    public static final
    Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID_2, "The restaurant 2", "Minsk", "WannaBeRestaurant.com");

    public static final String RESTAURANT_UPDATED_NAME = "Updated Name";
    public static final String RESTAURANT_UPDATED_ADDRESS = "Updated address";
    public static final String RESTAURANT_UPDATED_WEBSITE = "Updated website";

    public static Restaurant getNew() {
        Restaurant aNew = new Restaurant();
        aNew.setName("New Restaurant!");
        aNew.setAddress("New Address");
        aNew.setWebsite("New Website");
        return aNew;
    }

    public static Restaurant getUpdated(Restaurant restaurant) {
        Restaurant updated = new Restaurant(restaurant);
        updated.setName(RESTAURANT_UPDATED_NAME);
        updated.setAddress(RESTAURANT_UPDATED_ADDRESS);
        updated.setWebsite(RESTAURANT_UPDATED_WEBSITE);
        return updated;
    }
}