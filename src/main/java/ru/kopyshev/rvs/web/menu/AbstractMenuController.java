package ru.kopyshev.rvs.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.service.MenuService;
import ru.kopyshev.rvs.to.MenuDTO;
import ru.kopyshev.rvs.to.MenuUpdateDTO;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;
import static ru.kopyshev.rvs.util.DateTimeUtil.getMaxIfNull;
import static ru.kopyshev.rvs.util.DateTimeUtil.getMinIfNull;

@Slf4j
public abstract class AbstractMenuController {

    @Autowired
    private MenuService service;

    protected MenuDTO create(MenuUpdateDTO menuUpdateDTO) {
        log.info("creating menu items from menu {}", menuUpdateDTO);
        return service.create(menuUpdateDTO);
    }

    protected MenuDTO get(Integer id) {
        log.info("getting menu {}", id);
        return service.get(id);
    }

    protected List<MenuDTO> getAll(Integer restaurantId, LocalDate startParam, LocalDate endParam) {
        LocalDate start = getMinIfNull(startParam);
        LocalDate end = getMaxIfNull(endParam);
        log.info("getting menus of restaurant {} between dates {} and {}", restaurantId, start, end);
        return !isNull(restaurantId)
                ? service.getAll(restaurantId, start, end)
                : service.getAll(start, end);
    }

    protected void update(MenuUpdateDTO menuDTO, Integer id) {
        log.info("updating menu {} with id {}", menuDTO, id);
        service.update(menuDTO, id);
    }

    protected void delete(Integer id) {
        log.info("deleting menu {}", id);
        service.delete(id);
    }
}