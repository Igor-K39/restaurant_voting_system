package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.domain.Restaurant;
import ru.kopyshev.rvs.dto.RestaurantDTO;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapper {
    private final ModelMapper restaurantMapper;

    public RestaurantMapper() {
        this.restaurantMapper = new ModelMapper();
    }

    @PostConstruct
    public void setup() {
        restaurantMapper.createTypeMap(Restaurant.class, RestaurantDTO.class);
    }

    public Restaurant toEntity(RestaurantDTO restaurantDTO) {
        return restaurantMapper.map(restaurantDTO, Restaurant.class);
    }

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return restaurantMapper.map(restaurant, RestaurantDTO.class);
    }

    public List<RestaurantDTO> toDTO(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
