package ru.kopyshev.rvs.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class DishTo extends NamedTo {

    private RestaurantTo restaurant;

    public DishTo(DishTo to) {
        this(to.id, to.name, to.restaurant);
    }

    public DishTo(String name, RestaurantTo restaurant) {
        super(null, name);
        this.restaurant = restaurant;
    }

    public DishTo(Integer id, String name, RestaurantTo restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DishTo dishTo = (DishTo) o;
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
