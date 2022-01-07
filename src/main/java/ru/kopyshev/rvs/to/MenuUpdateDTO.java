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
public class MenuUpdateDTO extends NamedDTO implements HasId {

    @NotNull
    private Integer restaurantId;

    @NotNull
    private LocalDate dateOf;

    @NotEmpty
    private List<MenuUpdateDTO.MenuItemUpdateDTO> menuItems;

    public MenuUpdateDTO(MenuUpdateDTO dto) {
        this(dto.id, dto.name, dto.restaurantId, dto.dateOf, List.copyOf(dto.menuItems));
    }

    public MenuUpdateDTO(String name, Integer restaurantId, LocalDate dateOf) {
        this(null, name, restaurantId, dateOf, null);
    }

    public MenuUpdateDTO(String name, Integer restaurantId, LocalDate dateOf, List<MenuUpdateDTO.MenuItemUpdateDTO> menuItems) {
        this(null, name, restaurantId, dateOf, menuItems);
    }

    public MenuUpdateDTO(Integer id, String name, Integer restaurantId, LocalDate dateOf, List<MenuUpdateDTO.MenuItemUpdateDTO> menuItems) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.dateOf = dateOf;
        setMenuItems(menuItems);
    }

    public List<MenuUpdateDTO.MenuItemUpdateDTO> getMenuItems() {
        return List.copyOf(menuItems);
    }

    public void setMenuItems(List<MenuUpdateDTO.MenuItemUpdateDTO> menuItems) {
        this.menuItems = menuItems != null
                ? List.copyOf(menuItems)
                : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MenuUpdateDTO that = (MenuUpdateDTO) o;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(dateOf, that.dateOf) && Objects.equals(menuItems, that.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restaurantId, dateOf, menuItems);
    }

    @Override
    public String toString() {
        return "\nMenuUpdateDTO{" +
                "\n    id=" + id +
                "\n    name='" + name + '\'' +
                "\n    restaurantId=" + restaurantId +
                "\n    dateOf=" + dateOf +
                "\n    menuItems=" + menuItems +
                "\n}";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MenuItemUpdateDTO extends BaseDTO implements HasId {

        @NotNull
        private Integer dishId;

        @PositiveOrZero
        private int price;

        public MenuItemUpdateDTO(Integer dishId, int price) {
            this(null, dishId, price);
        }

        public MenuItemUpdateDTO(Integer id, Integer dishId, int price) {
            super(id);
            this.dishId = dishId;
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            MenuItemUpdateDTO that = (MenuItemUpdateDTO) o;
            return price == that.price && Objects.equals(dishId, that.dishId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), dishId, price);
        }

        @Override
        public String toString() {
            return "MenuItemUpdateDTO{" +
                    "id=" + id +
                    ", dishId=" + dishId +
                    ", price=" + price +
                    '}';
        }
    }
}
