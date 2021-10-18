package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dish")
@Access(AccessType.FIELD)
@NamedEntityGraph(name = "with-restaurant", attributeNodes = @NamedAttributeNode("restaurant"))
public class Dish extends NamedEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Dish item) {
        this(item.id, item.name, item.restaurant);
    }

    public Dish(String name, Restaurant restaurant) {
        this(null, name, restaurant);
    }

    public Dish(Integer id, String name, Restaurant restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", restaurant=" + restaurant.getId() +
                '}';
    }
}