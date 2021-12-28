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
public class MenuDTO {

    @NotNull
    private LocalDate dateOf;

    @NotNull
    protected RestaurantDTO restaurantTo;

    @NotEmpty
    private List<MenuItemDTO> menuItemTos;

    public MenuDTO(LocalDate dateOf, RestaurantDTO restaurantTo, List<MenuItemDTO> menuItems) {
        this.dateOf = dateOf;
        this.restaurantTo = restaurantTo;
        setMenuItemTos(menuItems);
    }

    public List<MenuItemDTO> getMenuItemTos() {
        return List.copyOf(menuItemTos);
    }

    public void setMenuItemTos(List<MenuItemDTO> menuItemTos) {
        this.menuItemTos = List.copyOf(menuItemTos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDTO menuDTO = (MenuDTO) o;
        return Objects.equals(dateOf, menuDTO.dateOf)
                && Objects.equals(restaurantTo, menuDTO.restaurantTo)
                && Objects.equals(menuItemTos, menuDTO.menuItemTos);
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
    public static class MenuItemDTO extends BaseDTO {

        @NotNull
        protected NamedDTO dishTo;

        @PositiveOrZero
        int price;

        public MenuItemDTO(NamedDTO dishTo, int price) {
            this(null, dishTo, price);
        }

        public MenuItemDTO(Integer id, NamedDTO dishTo, int price) {
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