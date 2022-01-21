package ru.kopyshev.rvs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.domain.Dish;
import ru.kopyshev.rvs.repository.DishRepository;
import ru.kopyshev.rvs.dto.dish.DishDTO;
import ru.kopyshev.rvs.dto.dish.DishUpdateDTO;
import ru.kopyshev.rvs.util.ValidationUtil;
import ru.kopyshev.rvs.util.mapper.DishMapper;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    public DishService(DishRepository dishRepository, DishMapper dishMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
    }

    @Transactional
    public DishDTO create(DishUpdateDTO dishUpdateDTO) {
        log.debug("Creating a new dish: {}", dishUpdateDTO);
        ValidationUtil.checkNew(dishUpdateDTO);
        Dish saved = dishRepository.save(dishMapper.toEntity(dishUpdateDTO));
        return dishMapper.toDTO(saved);
    }

    public DishDTO get(Integer id) {
        log.debug("Getting a dish with id {}", id);
        Dish dish = dishRepository.get(id).orElseThrow(() -> new NotFoundException(Dish.class, "id = " + id));
        return dishMapper.toDTO(dish);
    }

    @Transactional
    public void update(DishUpdateDTO dishUpdateDTO, Integer dishId) {
        log.debug("updating dish with id {} by {}", dishId, dishUpdateDTO);
        ValidationUtil.assureIdConsistent(dishUpdateDTO, dishId);
        dishRepository.save(dishMapper.toEntity(dishUpdateDTO));
    }

    public void delete(Integer dishId) {
        log.debug("deleting dish with id {}", dishId);
        int affectedRows = dishRepository.delete(dishId);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, Dish.class, dishId);
    }

    public List<DishDTO> getAll(Integer restaurantId) {
        log.debug("getting all dishes for restaurant with id {}", restaurantId);
        List<Dish> dishes = isNull(restaurantId)
                ? dishRepository.getAll().orElse(List.of())
                : dishRepository.getAll(restaurantId).orElse(List.of());
        return dishMapper.toDTO(dishes);
    }
}
