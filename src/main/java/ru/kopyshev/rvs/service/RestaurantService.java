package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.repository.CrudRestaurantRepository;
import ru.kopyshev.rvs.to.RestaurantDTO;
import ru.kopyshev.rvs.util.ValidationUtil;
import ru.kopyshev.rvs.util.mapper.RestaurantMapper;

import java.util.List;

@Service
public class RestaurantService {

    private final CrudRestaurantRepository repository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(CrudRestaurantRepository repository, RestaurantMapper restaurantMapper) {
        this.repository = repository;
        this.restaurantMapper = restaurantMapper;
    }

    public RestaurantDTO create(RestaurantDTO restaurantDTO) {
        ValidationUtil.checkNew(restaurantDTO);
        Restaurant saved = repository.save(restaurantMapper.toEntity(restaurantDTO));
        return restaurantMapper.toDTO(saved);
    }

    public RestaurantDTO get(int id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found a restaurant with id: " + id));
        return restaurantMapper.toDTO(restaurant);
    }

    public void update(RestaurantDTO restaurantDTO, int id) {
        ValidationUtil.assureIdConsistent(restaurantDTO, id);
        repository.save(restaurantMapper.toEntity(restaurantDTO));
    }

    public void delete(int id) {
        int affectedRows = repository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, id);
    }

    public List<RestaurantDTO> getAll() {
        return restaurantMapper.toDTO(repository.getAll().orElse(List.of()));
    }
}
