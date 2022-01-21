package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.domain.Dish;
import ru.kopyshev.rvs.domain.Restaurant;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.dto.dish.DishDTO;
import ru.kopyshev.rvs.dto.dish.DishUpdateDTO;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DishMapper {
    private final ModelMapper dishMapper;
    private final RestaurantRepository restaurantRepository;

    public DishMapper(RestaurantRepository restaurantRepository) {
        this.dishMapper = new ModelMapper();
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    public void setup() {
        dishMapper.createTypeMap(Dish.class, DishDTO.class);
        dishMapper.createTypeMap(DishUpdateDTO.class, Dish.class);
    }

    public Dish toEntity(DishUpdateDTO dishUpdateDTO) {
        Dish dish = dishMapper.map(dishUpdateDTO, Dish.class);
        Integer restaurantId = dishUpdateDTO.getRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(Objects.requireNonNull(restaurantId));
        dish.setRestaurant(restaurant);
        return dish;
    }

    public DishDTO toDTO(Dish dish) {
        return dishMapper.map(dish, DishDTO.class);
    }

    public List<DishDTO> toDTO(List<Dish> dishes) {
        return dishes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
