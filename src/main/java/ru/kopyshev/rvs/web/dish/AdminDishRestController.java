package ru.kopyshev.rvs.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kopyshev.rvs.service.DishService;
import ru.kopyshev.rvs.to.DishDTO;
import ru.kopyshev.rvs.to.DishUpdateDTO;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AdminDishRestController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {
    public static final String ADMIN_REST_URL = "/rest/admin/dishes";

    private final DishService dishService;

    public AdminDishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishDTO> createWithLocation(@RequestBody DishUpdateDTO dishUpdateDTO) {
        log.info("creating dish from dishUpdateDTO {}", dishUpdateDTO);
        var dishDTO = dishService.create(dishUpdateDTO);

        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ADMIN_REST_URL + "/{id}")
                .buildAndExpand(dishDTO.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(dishDTO);
    }

    @GetMapping("/{id}")
    public DishDTO get(@PathVariable("id") int dishId) {
        log.info("getting dish {}", dishId);
        return dishService.get(dishId);
    }

    @PutMapping(value = {"/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") int dishId, @RequestBody DishUpdateDTO dishUpdateDTO) {
        log.info("updating dish {} of by dishUpdateDTO {}", dishUpdateDTO.getId(), dishUpdateDTO);
        dishService.update(dishUpdateDTO, dishId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int dishId) {
        log.info("deleting dish {}", dishId);
        dishService.delete(dishId);
    }

    @GetMapping
    public List<DishDTO> getAll(@RequestParam(value = "restaurant", required = false) Integer restaurantId) {
        log.info("getting all the dishes for restaurant {}", restaurantId);
        return dishService.getAll(restaurantId);
    }
}