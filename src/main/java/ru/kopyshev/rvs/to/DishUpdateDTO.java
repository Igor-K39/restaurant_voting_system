package ru.kopyshev.rvs.to;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class DishUpdateDTO extends NamedDTO {
    private Integer restaurantId;

    public DishUpdateDTO(Integer id, String name, Integer restaurantId) {
        super(id, name);
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DishUpdateDTO that = (DishUpdateDTO) o;
        return Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restaurantId);
    }
}
