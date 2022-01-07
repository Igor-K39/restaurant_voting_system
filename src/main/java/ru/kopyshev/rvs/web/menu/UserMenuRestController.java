package ru.kopyshev.rvs.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kopyshev.rvs.to.MenuDTO;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuRestController extends AbstractMenuController {
    public static final String REST_URL = "/rest/menu";

    @Override
    @GetMapping("/{id}")
    public MenuDTO get(@PathVariable Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<MenuDTO> getAll(@RequestParam(name = "restaurant", required = false) Integer restaurantId,
                                @RequestParam(name = "start", required = false) LocalDate start,
                                @RequestParam(name = "end", required = false) LocalDate end) {
        return super.getAll(restaurantId, start, end);
    }
}
