package ru.kopyshev.rvs.testdata;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory;
import ru.kopyshev.rvs.dto.dish.DishDTO;
import ru.kopyshev.rvs.dto.dish.DishUpdateDTO;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;
import static ru.kopyshev.rvs.testdata.RestaurantTestData.*;

@UtilityClass
public class DishTestData {
    public static final int DISH_ID_1 = 100_004;
    public static final int DISH_ID_2 = 100_005;
    public static final int DISH_ID_3 = 100_006;
    public static final int DISH_ID_4 = 100_007;
    public static final int DISH_ID_5 = 100_008;
    public static final int DISH_ID_6 = 100_009;
    public static final DishUpdateDTO NEW_DISH_UPDATE_DTO = new DishUpdateDTO(null, "New Borsch", RESTAURANT_ID_1);
    public static final DishDTO NEW_DISH_DTO = new DishDTO(null, "New Borsch", RESTAURANT_TO_1);
    public static final DishDTO DISH_DTO_1 = new DishDTO(DISH_ID_1, "R1-Borsch", RESTAURANT_TO_1);
    public static final DishDTO DISH_DTO_2 = new DishDTO(DISH_ID_2, "R1-Beef", RESTAURANT_TO_1);
    public static final DishDTO DISH_DTO_3 = new DishDTO(DISH_ID_3, "R1-Pork", RESTAURANT_TO_1);
    public static final DishDTO DISH_DTO_4 = new DishDTO(DISH_ID_4, "R2-Potato", RESTAURANT_TO_2);
    public static final DishDTO DISH_DTO_5 = new DishDTO(DISH_ID_5, "R2-Mushrooms", RESTAURANT_TO_2);
    public static final DishDTO DISH_DTO_6 = new DishDTO(DISH_ID_6, "R2-Cabbage", RESTAURANT_TO_2);
    public static final MatcherFactory.Matcher<DishDTO> DISH_TO_MATCHER =
            usingIgnoreFieldComparator(DishDTO.class, "id");

    public static DishUpdateDTO getUpdated(DishDTO dishDTO, int restaurantId) {
        DishUpdateDTO updated = new DishUpdateDTO();
        updated.setId(dishDTO.id());
        updated.setName(dishDTO.getName() + "updated");
        updated.setRestaurantId(restaurantId);
        return updated;
    }
}