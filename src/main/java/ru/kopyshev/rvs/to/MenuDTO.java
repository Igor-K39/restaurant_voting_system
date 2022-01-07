package ru.kopyshev.rvs.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kopyshev.rvs.HasId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class MenuDTO extends NamedDTO implements HasId {

    @NotNull
    private RestaurantDTO restaurant;

    @NotNull
    private LocalDate dateOf;

    @NotEmpty
    private List<MenuItemDTO> menuItems = new ArrayList<>();

    public MenuDTO(MenuDTO menuDTO) {
        super(menuDTO.id, menuDTO.name);
        this.restaurant = menuDTO.restaurant;
        this.dateOf = menuDTO.dateOf;
        this.menuItems = List.copyOf(menuDTO.menuItems);
    }

    public MenuDTO(String name, RestaurantDTO restaurant, LocalDate dateOf, List<MenuItemDTO> menuItems) {
        this(null, name, restaurant, dateOf, menuItems);
    }

    public MenuDTO(Integer id, String name, RestaurantDTO restaurant, LocalDate dateOf, List<MenuItemDTO> menuItems) {
        super(id, name);
        this.dateOf = dateOf;
        this.restaurant = restaurant;
        setMenuItems(menuItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDTO menuDTO = (MenuDTO) o;
        return Objects.equals(dateOf, menuDTO.dateOf)
                && Objects.equals(restaurant, menuDTO.restaurant)
                && Objects.equals(menuItems, menuDTO.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOf, restaurant, menuItems);
    }

    @Override
    public String toString() {
        return "\n    MenuDTO{" +
                "\n    id=" + id +
                "\n    restaurant=" + restaurant +
                "\n    name='" + name + '\'' +
                "\n    dateOf=" + dateOf +
                "\n    menuItems=" + menuItems +
                "\n}";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MenuItemDTO extends BaseDTO implements HasId {

        @NotNull
        private NamedDTO dish;

        @PositiveOrZero
        int price;

        public MenuItemDTO(NamedDTO dish, int price) {
            this(null, dish, price);
        }

        public MenuItemDTO(Integer id, NamedDTO dish, int price) {
            super(id);
            this.dish = dish;
            this.price = price;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{" +
                    "id=" + id +
                    ", dishTo=" + dish +
                    ", price=" + price +
                    '}';
        }
    }
}