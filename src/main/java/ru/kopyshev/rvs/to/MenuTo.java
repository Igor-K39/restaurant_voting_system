package ru.kopyshev.rvs.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class MenuTo{

    @NotNull
    private LocalDate dateOf;

    @NotNull
    protected RestaurantTo restaurantTo;

    @NotEmpty
    private List<MenuItemTo> menuItemTos;

    public MenuTo(LocalDate dateOf, RestaurantTo restaurantTo, List<MenuItemTo> menuItems) {
        this.dateOf = dateOf;
        this.restaurantTo = restaurantTo;
        setMenuItemTos(menuItems);
    }

    public List<MenuItemTo> getMenuItemTos() {
        return List.copyOf(menuItemTos);
    }

    public void setMenuItemTos(List<MenuItemTo> menuItemTos) {
        this.menuItemTos = List.copyOf(menuItemTos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTo menuTo = (MenuTo) o;
        return Objects.equals(dateOf, menuTo.dateOf)
                && Objects.equals(restaurantTo, menuTo.restaurantTo)
                && Objects.equals(menuItemTos, menuTo.menuItemTos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOf, restaurantTo, menuItemTos);
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "dateOf=" + dateOf +
                ", restaurant=" + restaurantTo +
                ", menuItemTos=" + menuItemTos +
                '}';
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MenuItemTo extends BaseTo {

        @NotNull
        protected NamedTo dishTo;

        @PositiveOrZero
        int price;

        public MenuItemTo(NamedTo dishTo, int price) {
            this(null, dishTo, price);
        }

        public MenuItemTo(Integer id, NamedTo dishTo, int price) {
            super(id);
            this.dishTo = dishTo;
            this.price = price;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{" +
                    "id=" + id +
                    ", dishTo=" + dishTo +
                    ", price=" + price +
                    '}';
        }
    }
}