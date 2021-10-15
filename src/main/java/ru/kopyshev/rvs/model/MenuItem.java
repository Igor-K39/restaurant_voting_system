package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MenuItem extends BaseEntity {

    private Dish dish;

    private Restaurant restaurant;

    private LocalDate dateOf;

    private int price;

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.restaurant, menuItem.dish, menuItem.dateOf, menuItem.price);
    }

    public MenuItem(Dish dish, Restaurant restaurant, LocalDate dateOf, int price) {
        this(null, restaurant, dish, dateOf, price);
    }

    public MenuItem(Integer id, Restaurant restaurant, Dish dish, LocalDate dateOf, int price) {
        super(id);
        this.restaurant = restaurant;
        this.dateOf = dateOf;
        this.dish = dish;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", dateOf=" + dateOf +
                '}';
    }
}
