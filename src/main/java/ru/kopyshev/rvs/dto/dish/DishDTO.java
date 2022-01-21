package ru.kopyshev.rvs.dto.dish;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.kopyshev.rvs.dto.NamedDTO;
import ru.kopyshev.rvs.dto.RestaurantDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class DishDTO extends NamedDTO {

    private RestaurantDTO restaurant;

    public DishDTO(DishDTO dto) {
        this(dto.id, dto.name, dto.restaurant);
    }

    public DishDTO(Integer id, String name, RestaurantDTO restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }
}
