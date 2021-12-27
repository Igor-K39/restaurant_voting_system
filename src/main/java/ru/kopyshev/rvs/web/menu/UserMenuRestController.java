package ru.kopyshev.rvs.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kopyshev.rvs.to.MenuTo;
import ru.kopyshev.rvs.web.restaurant.UserRestaurantRestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuRestController extends AbstractMenuController {
    public static final String REST_URL = UserRestaurantRestController.REST_URL;

    @Override
    @GetMapping("/{restaurantId}/menu")
    public MenuTo get(@PathVariable("restaurantId") int restaurantId,
                      @RequestParam("date") LocalDate dateParam) {
        return super.get(restaurantId, dateParam);
    }

    @Override
    @GetMapping("/{restaurantId}/menu/all")
    public List<MenuTo> getAll(@PathVariable("restaurantId") int restaurantId,
                               @RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        return super.getAll(restaurantId, start, end);
    }

    @Override
    @GetMapping("/menu")
    public List<MenuTo> getAll(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        return super.getAll(start, end);
    }
}
