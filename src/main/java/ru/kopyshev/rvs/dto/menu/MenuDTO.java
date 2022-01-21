package ru.kopyshev.rvs.dto.menu;

import lombok.*;
import ru.kopyshev.rvs.HasId;
import ru.kopyshev.rvs.dto.BaseDTO;
import ru.kopyshev.rvs.dto.NamedDTO;
import ru.kopyshev.rvs.dto.RestaurantDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class MenuDTO extends NamedDTO implements HasId {
    @NotNull
    private RestaurantDTO restaurant;

    @NotNull
    private LocalDate dateOf;

    @NotEmpty
    private List<MenuItemDTO> menuItems = new ArrayList<>();

    public MenuDTO(String name, RestaurantDTO restaurant, LocalDate dateOf, List<MenuItemDTO> menuItems) {
        this(null, name, restaurant, dateOf, menuItems);
    }

    public MenuDTO(Integer id, String name, RestaurantDTO restaurant, LocalDate dateOf, List<MenuItemDTO> menuItems) {
        super(id, name);
        this.dateOf = dateOf;
        this.restaurant = restaurant;
        setMenuItems(menuItems);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
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
    }
}