package ru.kopyshev.rvs.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kopyshev.rvs.service.DishService;
import ru.kopyshev.rvs.to.DishTo;
import ru.kopyshev.rvs.util.DishUtil;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = AdminDishController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    public static final String ADMIN_REST_URL = "/rest/admin/restaurants/{restaurantId}/dishes";
    private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private final DishService dishService;

    public AdminDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@PathVariable("restaurantId") int restaurantId,
                                                     @RequestBody DishTo dishTo) {
        log.info("creating dish of restaurant {} from named to {}", restaurantId, dishTo);
        var dish = DishUtil.getDishFromTo(dishTo);
        dish = dishService.create(dish);
        var map = Map.of("restaurantId", restaurantId, "id", dish.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ADMIN_REST_URL + "/{restaurantId}/dishes/{id}")
                .buildAndExpand(map)
                .toUri();
        dishTo.setId(dish.getId());
        return ResponseEntity.created(uriOfNewResource).body(dishTo);
    }

    @GetMapping("/{dishId}")
    public DishTo get(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        log.info("getting dish {} of restaurant {}", dishId, restaurantId);
        var dish = dishService.get(dishId, restaurantId);
        return DishUtil.getToFromDish(dish);
    }

    @PutMapping(value = {"/{dishId}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId,
                       @RequestBody DishTo dishTo) {
        log.info("updating dish {} of restaurant {} by namedTo {}", dishTo.getId(), restaurantId, dishTo);
        var dish = DishUtil.getDishFromTo(dishTo);
        dishService.update(dish, dishId, restaurantId);
    }

    @DeleteMapping("{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        log.info("deleting dish {} of restaurant {}", dishId, restaurantId);
        dishService.delete(dishId, restaurantId);
    }

    @GetMapping
    public List<DishTo> getAll(@PathVariable("restaurantId") int restaurantId) {
        log.info("getting all the dishes for restaurant {}", restaurantId);
        var dishes = dishService.getAll(restaurantId);
        return DishUtil.getToFromDish(dishes);
    }
}