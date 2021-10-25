package ru.kopyshev.rvs.web.menu;

import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopyshev.rvs.to.MenuTo;
import ru.kopyshev.rvs.web.restaurant.UserRestaurantController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuController extends AbstractMenuController {
    public static final String REST_URL = UserRestaurantController.REST_URL;

    @Override
    @GetMapping("/{restaurantId}/menu")
    public List<MenuTo> getAll(@PathVariable("restaurantId") int restaurantId,
                               @Param("start") LocalDate start, @Param("end") LocalDate end) {
        return super.getAll(restaurantId, start, end);
    }

    @Override
    @GetMapping("/menu")
    public List<MenuTo> getAll(@Param("start") LocalDate start, @Param("end") LocalDate end) {
        return super.getAll(start, end);
    }
}
