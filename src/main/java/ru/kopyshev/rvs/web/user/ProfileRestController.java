package ru.kopyshev.rvs.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kopyshev.rvs.dto.user.UserDTO;
import ru.kopyshev.rvs.web.SecurityUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserController{
    static final String REST_URL = "/rest/profile";

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createWithLocation(@RequestBody @Valid UserDTO userTo) {
        UserDTO created = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("")
    public UserDTO get() {
        int userId = SecurityUtil.authUserId();
        return super.get(userId);
    }

    @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@RequestBody Map<String, Object> updates) {
        int userId = SecurityUtil.authUserId();
        super.update(updates, userId, "name", "email", "password");
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        int userId = SecurityUtil.authUserId();
        super.delete(userId);
    }
}