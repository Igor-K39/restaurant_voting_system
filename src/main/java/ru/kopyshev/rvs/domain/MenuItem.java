package ru.kopyshev.rvs.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "menu_item")
@Access(AccessType.FIELD)
public class MenuItem extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_item_id")
    private Dish dish;

    @PositiveOrZero
    @Column(name = "price")
    private int price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    public MenuItem(Integer id, Dish dish, int price, Menu menu) {
        super(id);
        this.dish = dish;
        this.price = price;
        this.menu = menu;
    }
}
