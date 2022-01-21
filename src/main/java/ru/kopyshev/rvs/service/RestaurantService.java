package ru.kopyshev.rvs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.domain.Restaurant;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.dto.RestaurantDTO;
import ru.kopyshev.rvs.util.ValidationUtil;
import ru.kopyshev.rvs.util.mapper.RestaurantMapper;

import java.util.List;

@Slf4j
@Service
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository repository, RestaurantMapper restaurantMapper) {
        this.repository = repository;
        this.restaurantMapper = restaurantMapper;
    }

    public RestaurantDTO create(RestaurantDTO restaurantDTO) {
        log.debug("Creating a new restaurant: {}", restaurantDTO);
        ValidationUtil.checkNew(restaurantDTO);
        Restaurant saved = repository.save(restaurantMapper.toEntity(restaurantDTO));
        return restaurantMapper.toDTO(saved);
    }

    public RestaurantDTO get(Integer id) {
        log.debug("Getting restaurant with id {}", id);
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Restaurant.class, "id = " + id));
        return restaurantMapper.toDTO(restaurant);
    }

    public void update(RestaurantDTO restaurantDTO, Integer id) {
        log.debug("Updating restaurant {} by {}", id, restaurantDTO);
        ValidationUtil.assureIdConsistent(restaurantDTO, id);
        repository.save(restaurantMapper.toEntity(restaurantDTO));
    }

    public void delete(Integer id) {
        log.debug("Deleting restaurant with id {}", id);
        int affectedRows = repository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, Restaurant.class, id);
    }

    public List<RestaurantDTO> getAll() {
        log.debug("Getting all restaurants");
        return restaurantMapper.toDTO(repository.getAll().orElse(List.of()));
    }
}
