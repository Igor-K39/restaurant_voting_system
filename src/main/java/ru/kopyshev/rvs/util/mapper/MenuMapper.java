package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.domain.Menu;
import ru.kopyshev.rvs.domain.MenuItem;
import ru.kopyshev.rvs.domain.Restaurant;
import ru.kopyshev.rvs.repository.DishRepository;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.dto.menu.MenuDTO;
import ru.kopyshev.rvs.dto.menu.MenuUpdateDTO;
import ru.kopyshev.rvs.dto.RestaurantDTO;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {
    private final ModelMapper menuMapper;
    private final RestaurantMapper restaurantMapper;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuMapper(RestaurantRepository restaurantRepository, DishRepository dishRepository,
                      RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        menuMapper = new ModelMapper();
    }

    @PostConstruct
    public void setup() {
        Converter<Restaurant, RestaurantDTO> restaurantToDto = ctx -> restaurantMapper.toDTO(ctx.getSource());
        Converter<RestaurantDTO, Restaurant> restaurantToEntity = ctx -> restaurantMapper.toEntity(ctx.getSource());
        Converter<MenuUpdateDTO, Menu> menuUpdateToEntity = ctx -> {
            Restaurant restaurant = restaurantRepository.getById(ctx.getSource().getRestaurantId());
            Menu menu = ctx.getDestination();
            menu.setRestaurant(restaurant);
            menu.getMenuItems().forEach(item -> {
                int dishId = item.getDish().id();
                item.setDish(dishRepository.getById(dishId));
                item.setMenu(menu);
            });
            return menu;
        };
        menuMapper.addConverter(restaurantToDto);
        menuMapper.addConverter(restaurantToEntity);
        menuMapper.createTypeMap(Menu.class, MenuDTO.class);
        menuMapper.createTypeMap(MenuItem.class, MenuDTO.MenuItemDTO.class);
        menuMapper.createTypeMap(MenuUpdateDTO.class, Menu.class)
                .addMappings(m -> m.skip(Menu::setRestaurant))
                .setPostConverter(menuUpdateToEntity);
    }

    public Menu toEntity(MenuUpdateDTO menuUpdateDTO) {
        return menuMapper.map(menuUpdateDTO, Menu.class);
    }

    public MenuDTO toDTO(Menu menu) {
        return menuMapper.map(menu, MenuDTO.class);
    }

    public List<MenuDTO> toDTO(List<Menu> menus) {
        return menus.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
