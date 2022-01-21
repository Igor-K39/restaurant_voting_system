package ru.kopyshev.rvs.dto.menu;

import lombok.*;
import ru.kopyshev.rvs.HasId;
import ru.kopyshev.rvs.dto.BaseDTO;
import ru.kopyshev.rvs.dto.NamedDTO;

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

    @Data
    @EqualsAndHashCode(callSuper = true)
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
    }
}
