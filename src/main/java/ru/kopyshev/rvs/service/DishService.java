package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.Dish;
import ru.kopyshev.rvs.repository.CrudDishRepository;
import ru.kopyshev.rvs.util.CollectionUtil;
import ru.kopyshev.rvs.util.ValidationUtil;

import java.util.List;

@Service
public class DishService {
    private final CrudDishRepository repository;

    public DishService(CrudDishRepository repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish) {
        ValidationUtil.checkNew(dish);
        return repository.save(dish);
    }

    public Dish get(int id, int restaurant_id) {
        return repository.get(id, restaurant_id)
                .orElseThrow(() -> new NotFoundException("Not found dish with id: " + id));
    }

    public void update(Dish dish, int dishId, int restaurant_id) {
        ValidationUtil.assureIdConsistent(dish.getRestaurant(), restaurant_id);
        ValidationUtil.assureIdConsistent(dish, dishId);
        repository.save(dish);
    }

    public void delete(int id, int restaurant_id) {
        int affectedRows = repository.delete(id, restaurant_id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, id);
    }

    public List<Dish> getAll(int restaurantId) {
        return CollectionUtil.getImmutableListIfNull(repository.getAll(restaurantId));
    }
}
