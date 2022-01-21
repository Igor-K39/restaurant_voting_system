package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.domain.Dish;
import ru.kopyshev.rvs.domain.Restaurant;
import ru.kopyshev.rvs.dto.dish.DishDTO;
import ru.kopyshev.rvs.dto.dish.DishUpdateDTO;
import ru.kopyshev.rvs.repository.RestaurantRepository;

import javax.annotation.PostConstruct;
import java.util.List;
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
        Converter<DishUpdateDTO, Dish> updateDtoPostConverter = ctx -> {
            Restaurant restaurant = restaurantRepository.getById(ctx.getSource().getRestaurantId());
            ctx.getDestination().setRestaurant(restaurant);
            return ctx.getDestination();
        };
        dishMapper.createTypeMap(Dish.class, DishDTO.class);
        dishMapper.createTypeMap(DishUpdateDTO.class, Dish.class)
                .addMappings(m -> m.skip(Dish::setRestaurant))
                .setPostConverter(updateDtoPostConverter);
    }

    public Dish toEntity(DishUpdateDTO dto) {
        return dishMapper.map(dto, Dish.class);
    }

    public DishDTO toDTO(Dish dish) {
        return dishMapper.map(dish, DishDTO.class);
    }

    public List<DishDTO> toDTO(List<Dish> dishes) {
        return dishes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
