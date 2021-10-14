package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity {

    private Restaurant restaurant;

    public Dish(Dish item) {
        this(item.id, item.name, item.restaurant);
    }

    public Dish(String name, Restaurant restaurant, int basePrice) {
        this(null, name, restaurant);
    }

    public Dish(Integer id, String name, Restaurant restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}