package ru.kopyshev.rvs.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kopyshev.rvs.dto.RestaurantDTO;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantRestController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractRestaurantController {
    public static final String ADMIN_REST_URL = "/rest/admin/restaurants";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDTO> createWithLocation(@RequestBody @Valid RestaurantDTO restaurantTo) {
        RestaurantDTO created = super.create(restaurantTo);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ADMIN_REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @GetMapping("/{id}")
    public RestaurantDTO get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<RestaurantDTO> getAll() {
        return super.getAll();
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid RestaurantDTO restaurantDTO, @PathVariable("id") int id) {
        super.update(restaurantDTO, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
