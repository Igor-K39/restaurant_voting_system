package ru.kopyshev.rvs.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kopyshev.rvs.dto.menu.MenuDTO;
import ru.kopyshev.rvs.dto.menu.MenuUpdateDTO;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractMenuController {
    public static final String REST_URL = "/rest/admin/menu";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuDTO> createWithLocation(@RequestBody @Valid MenuUpdateDTO menuUpdateDTO) {
        MenuDTO created = super.create(menuUpdateDTO);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "{restaurantId}/menu")
                .buildAndExpand(created.getRestaurant().getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

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

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid MenuUpdateDTO menuUpdateDTO, @PathVariable Integer id) {
        super.update(menuUpdateDTO, id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected void delete(@PathVariable("id") Integer id) {
        super.delete(id);
    }
}