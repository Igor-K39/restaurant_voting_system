package ru.kopyshev.rvs.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.service.RestaurantService;
import ru.kopyshev.rvs.dto.RestaurantDTO;

import java.util.List;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantService service;

    protected RestaurantDTO create(RestaurantDTO restaurantDTO) {
        log.info("create restaurant from {}", restaurantDTO);
        return service.create(restaurantDTO);
    }

    protected RestaurantDTO get(int id) {
        log.info("getting restaurant with id = {}", id);
        return service.get(id);
    }

    protected List<RestaurantDTO> getAll() {
        log.info("getting all restaurants");
        return service.getAll();
    }

    protected void update(RestaurantDTO restaurantDTO, int id) {
        log.info("updating restaurant with id = {} by restaurantDTO {}", id, restaurantDTO);
        service.update(restaurantDTO, id);
    }

    protected void delete(int id) {
        log.info("deleting restaurant with id {}", id);
        service.delete(id);
    }
}