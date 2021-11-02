package ru.kopyshev.rvs.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.service.RestaurantService;
import ru.kopyshev.rvs.to.RestaurantTo;
import ru.kopyshev.rvs.util.RestaurantUtil;

import java.util.List;

import static ru.kopyshev.rvs.util.RestaurantUtil.getRestaurantFromTo;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    @Autowired
    private RestaurantService service;

    protected RestaurantTo create(RestaurantTo restaurantTo) {
        log.info("create restaurant from {}", restaurantTo);
        var restaurant = getRestaurantFromTo(restaurantTo);
        restaurant = service.create(restaurant);
        restaurantTo.setId(restaurant.id());
        return restaurantTo;
    }

    protected RestaurantTo get(int id) {
        log.info("getting restaurant with id = {}", id);
        return RestaurantUtil.getToFromRestaurant(service.get(id));
    }

    protected List<RestaurantTo> getAll() {
        log.info("getting all restaurants");
        return RestaurantUtil.getToFromRestaurant(service.getAll());
    }

    protected void update(RestaurantTo restaurantTo, int id) {
        log.info("updating restaurant with id = {} by restaurantTo {}", id, restaurantTo);
        var restaurant = getRestaurantFromTo(restaurantTo);
        service.update(restaurant, id);
    }

    protected void delete(int id) {
        log.info("deleting restaurant with id {}", id);
        service.delete(id);
    }
}