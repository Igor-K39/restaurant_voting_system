package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu_item")
@Access(AccessType.FIELD)
@NamedEntityGraph(
        name = "menu_item_full",
        attributeNodes = {
                @NamedAttributeNode(value = "dish"),
                @NamedAttributeNode(value = "restaurant")
        }
)
public class MenuItem extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_item_id")
    private Dish dish;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotNull
    @Column(name = "date_of")
    private LocalDate dateOf;

    @PositiveOrZero
    @Column(name = "price")
    private int price;

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.restaurant, menuItem.dish, menuItem.dateOf, menuItem.price);
    }

    public MenuItem(Restaurant restaurant, Dish dish, LocalDate dateOf, int price) {
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
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", dish=" + dish +
                ", dateOf=" + dateOf +
                ", price=" + price +
                '}';
    }
}
