package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.model.Menu;
import ru.kopyshev.rvs.model.MenuItem;
import ru.kopyshev.rvs.model.Restaurant;
import ru.kopyshev.rvs.repository.CrudDishRepository;
import ru.kopyshev.rvs.repository.CrudRestaurantRepository;
import ru.kopyshev.rvs.to.MenuDTO;
import ru.kopyshev.rvs.to.MenuUpdateDTO;
import ru.kopyshev.rvs.to.RestaurantDTO;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {
    private final ModelMapper menuMapper;
    private RestaurantMapper restaurantMapper;
    private CrudDishRepository dishRepository;
    private CrudRestaurantRepository restaurantRepository;

    private final Converter<Restaurant, RestaurantDTO> restaurantToDtoConverter =
            ctx -> ctx.getSource() != null ? restaurantMapper.toDTO(ctx.getSource()) : null;

    private final Converter<RestaurantDTO, Restaurant> restaurantDtoToEntityConverter =
            ctx -> ctx.getSource() != null ? restaurantMapper.toEntity(ctx.getSource()) : null;


    private final Converter<MenuUpdateDTO, Menu> updateDTOtoEntityPostConverter =
            ctx -> {
                Integer restaurantId = ctx.getSource().getRestaurantId();
                Restaurant restaurant = restaurantRepository.getById(restaurantId);
                Menu menu = ctx.getDestination();
                menu.setRestaurant(restaurant);
                List<MenuItem> items = menu.getMenuItems();

                items.forEach(item -> {
                    int dishId = item.getDish().id();
                    item.setDish(dishRepository.getById(dishId));
                    item.setMenu(menu);

                });
                return menu;
            };

    public MenuMapper(RestaurantMapper restaurantMapper,
                      CrudRestaurantRepository restaurantRepository, CrudDishRepository dishRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        menuMapper = new ModelMapper();
    }

    @PostConstruct
    public void setup() {
        menuMapper.createTypeMap(Menu.class, MenuDTO.class);
        menuMapper.createTypeMap(MenuUpdateDTO.class, Menu.class)
                .addMappings(m -> m.skip(Menu::setRestaurant))
                .setPostConverter(updateDTOtoEntityPostConverter);

        menuMapper.createTypeMap(MenuItem.class, MenuDTO.MenuItemDTO.class);
        menuMapper.addConverter(restaurantToDtoConverter);
        menuMapper.addConverter(restaurantDtoToEntityConverter);
    }

    public Menu getEntity(MenuUpdateDTO menuUpdateDTO) {
        return menuMapper.map(menuUpdateDTO, Menu.class);
    }

    public Menu getEntity(MenuDTO menuDTO) {
        Menu menu = menuMapper.map(menuDTO, Menu.class);
        Restaurant restaurant = menu.getRestaurant();
        menu.getMenuItems().forEach(item -> item.getDish().setRestaurant(restaurant));
        return menu;
    }

    public MenuDTO getDTO(Menu menu) {
        return menuMapper.map(menu, MenuDTO.class);
    }

    public List<MenuDTO> getDTO(List<Menu> menus) {
        return menus.stream()
                .map(this::getDTO)
                .collect(Collectors.toList());
    }
}
