package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.Dish;
import ru.kopyshev.rvs.repository.CrudDishRepository;
import ru.kopyshev.rvs.repository.CrudRestaurantRepository;
import ru.kopyshev.rvs.to.DishDTO;
import ru.kopyshev.rvs.to.DishUpdateDTO;
import ru.kopyshev.rvs.util.ValidationUtil;
import ru.kopyshev.rvs.util.mapper.DishMapper;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class DishService {
    private final CrudDishRepository dishRepository;
    private final DishMapper dishMapper;

    public DishService(CrudDishRepository dishRepository, CrudRestaurantRepository restaurantRepository,
                       DishMapper dishMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
    }

    @Transactional
    public DishDTO create(DishUpdateDTO dishUpdateDTO) {
        ValidationUtil.checkNew(dishUpdateDTO);
        Dish saved = dishRepository.save(dishMapper.toEntity(dishUpdateDTO));
        return dishMapper.toDTO(saved);
    }

    public DishDTO get(int id) {
        Dish dish = dishRepository.get(id).orElseThrow(() -> new NotFoundException("Not found dish with id: " + id));
        return dishMapper.toDTO(dish);
    }

    @Transactional
    public void update(DishUpdateDTO dishUpdateDTO, int dishId) {
        ValidationUtil.assureIdConsistent(dishUpdateDTO, dishId);
        dishRepository.save(dishMapper.toEntity(dishUpdateDTO));
    }

    public void delete(int id) {
        int affectedRows = dishRepository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, id);
    }

    public List<DishDTO> getAll(Integer restaurantId) {
        List<Dish> dishes = isNull(restaurantId)
                ? dishRepository.getAll().orElse(List.of())
                : dishRepository.getAll(restaurantId).orElse(List.of());
        return dishMapper.toDTO(dishes);
    }
}
