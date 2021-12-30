package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.Dish;
import ru.kopyshev.rvs.model.MenuItem;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.to.MenuDTO;
import ru.kopyshev.rvs.to.NamedDTO;
import ru.kopyshev.rvs.util.mapper.RestaurantMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MenuUtil {

    private final RestaurantMapper restaurantMapper = new RestaurantMapper();

    public static List<MenuItem> getMenuItems(MenuDTO menuDTO) {
        return menuDTO.getMenuItemTos().stream()
                .map(menuItemTo -> {
                    var id = menuItemTo.getId();
                    var price = menuItemTo.getPrice();
                    var dishTo = menuItemTo.getDishTo();
                    var dateOf = menuDTO.getDateOf();
                    var restaurantDTO = menuDTO.getRestaurantTo();
                    var dish = new Dish(dishTo.getId(), dishTo.getName(), restaurantMapper.toEntity(restaurantDTO));
                    return new MenuItem(id, restaurantMapper.toEntity(restaurantDTO), dish, dateOf, price);
                })
                .collect(Collectors.toList());
    }

    public static MenuDTO getMenuTo(Restaurant restaurant, LocalDate date, List<MenuItem> items) {
        var menuItemTos = items
                .stream()
                .filter(item -> item.getRestaurant().equals(restaurant) && item.getDateOf().equals(date))
                .map(item -> {
                    var id = item.getId();
                    var dishTo = new NamedDTO(item.getDish().id(), item.getDish().getName());
                    var price = item.getPrice();
                    return new MenuDTO.MenuItemDTO(id, dishTo, price);
                }).collect(Collectors.toList());
        return new MenuDTO(date, restaurantMapper.toDTO(restaurant), menuItemTos);
    }


    public static List<MenuDTO> getMenuTos(List<MenuItem> items) {
        var groupedMenuItems = items.stream()
                .collect(Collectors.groupingBy(MenuItem::getRestaurant));

        return groupedMenuItems.entrySet()
                .stream()
                .map(entry -> getMenuTos(entry.getKey(), entry.getValue()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static List<MenuDTO> getMenuTos(Restaurant restaurant, List<MenuItem> items) {
        var groupedMenuItems = items.stream()
                .filter(item -> item.getRestaurant().equals(restaurant))
                .collect(Collectors.groupingBy(MenuItem::getDateOf));

        return groupedMenuItems.entrySet()
                .stream()
                .map(entity -> MenuUtil.getMenuTo(restaurant, entity.getKey(), entity.getValue()))
                .collect(Collectors.toList());
    }
}
