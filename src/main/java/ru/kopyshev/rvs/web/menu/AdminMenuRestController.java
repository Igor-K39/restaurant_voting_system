package ru.kopyshev.rvs.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kopyshev.rvs.to.MenuTo;
import ru.kopyshev.rvs.web.restaurant.AdminRestaurantRestController;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractMenuController {
    public static final String REST_URL = AdminRestaurantRestController.ADMIN_REST_URL + "/";

    @PostMapping(value = "menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTo> createWithLocation(@RequestBody MenuTo menuTo) {
        var created = super.create(menuTo);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "{restaurantId}/menu")
                .buildAndExpand(created.getRestaurantTo().getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(menuTo);
    }

    @Override
    @GetMapping("{restaurantId}/menu")
    public List<MenuTo> getAll(@PathVariable("restaurantId") int restaurantId,
                               @RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        return super.getAll(restaurantId, start, end);
    }

    @Override
    @GetMapping("menu")
    protected List<MenuTo> getAll(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        return super.getAll(start, end);
    }

    @Override
    @PutMapping(value = "menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody MenuTo menuTo) {
        super.update(menuTo);
    }

    @Override
    @DeleteMapping(value = "{restaurantId}/menu/{date}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("date") LocalDate date) {
        service.deleteAtDate(restaurantId, date);
    }
}