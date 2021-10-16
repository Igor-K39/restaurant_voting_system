package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.repository.CrudRestaurantRepository;
import ru.kopyshev.rvs.util.ValidationUtil;

import java.util.List;

@Service
public class RestaurantService {

    private final CrudRestaurantRepository repository;

    public RestaurantService(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        return repository.save(restaurant);
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found a restaurant with id: " + id));
    }

    public void update(Restaurant restaurant, int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    public void delete(int id) {
        int affectedRows = repository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }
}
