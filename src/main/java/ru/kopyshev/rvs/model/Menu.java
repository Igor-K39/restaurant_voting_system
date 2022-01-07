package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu")
@Access(AccessType.FIELD)
@NamedEntityGraph(
        name = "menuWithAllFields",
        attributeNodes = {
                @NamedAttributeNode("restaurant"),
                @NamedAttributeNode(value = "menuItems", subgraph = "itemDishSubgraph"),
        },
        subgraphs = {
                @NamedSubgraph(name = "itemDishSubgraph",
                        attributeNodes = @NamedAttributeNode(value = "dish"))
        })
public class Menu extends NamedEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotNull
    @Column(name = "date_of")
    LocalDate dateOf;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id")
    List<MenuItem> menuItems = new ArrayList<>();

    public Menu(Integer id, String name, Restaurant restaurant, LocalDate dateOf) {
        this(id, name, restaurant, dateOf, null);
    }

    public Menu(String name, Restaurant restaurant, LocalDate dateOf, List<MenuItem> menuItems) {
        this(null, name, restaurant, dateOf, menuItems);
    }

    public Menu(Integer id, String name, Restaurant restaurant, LocalDate dateOf, List<MenuItem> menuItems) {
        super(id, name);
        this.restaurant = restaurant;
        this.dateOf = dateOf;
        this.menuItems = isNull(menuItems) ? this.menuItems : menuItems;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "\n    id=" + id +
                "\n    name='" + name + '\'' +
                "\n    restaurant=" + restaurant +
                "\n    dateOf=" + dateOf +
                "\n    menuItems=" + menuItems +
                "\n}";

    }
}
