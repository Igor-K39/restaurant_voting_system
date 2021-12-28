package ru.kopyshev.rvs.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class DishDTO extends NamedDTO {

    private RestaurantDTO restaurant;

    public DishDTO(DishDTO to) {
        this(to.id, to.name, to.restaurant);
    }

    public DishDTO(String name, RestaurantDTO restaurant) {
        super(null, name);
        this.restaurant = restaurant;
    }

    public DishDTO(Integer id, String name, RestaurantDTO restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DishDTO dishTo = (DishDTO) o;
        return Objects.equals(restaurant, dishTo.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restaurant);
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                '}';
    }
}
