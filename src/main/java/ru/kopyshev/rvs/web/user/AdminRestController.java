package ru.kopyshev.rvs.web.user;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kopyshev.rvs.to.UserTo;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = AdminRestController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController extends AbstractUserController {
    static final String ADMIN_REST_URL = "/rest/admin/users";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTo> createWithLocation(@RequestBody UserTo userTo) {
        UserTo created = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ADMIN_REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @GetMapping("/{id}")
    public UserTo get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("")
    public List<UserTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/by")
    public UserTo getByEmail(@Param("email") String email) {
        return super.getByEmail(email);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@RequestBody Map<String, Object> updates, @PathVariable int id) {
        super.update(updates, id, "name", "email", "password", "enabled", "roles");
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }
}
