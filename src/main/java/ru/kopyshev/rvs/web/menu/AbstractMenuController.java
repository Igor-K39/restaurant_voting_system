package ru.kopyshev.rvs.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.service.MenuService;
import ru.kopyshev.rvs.to.MenuDTO;
import ru.kopyshev.rvs.util.DateTimeUtil;
import ru.kopyshev.rvs.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractMenuController {

    @Autowired
    MenuService service;

    protected MenuDTO create(MenuDTO menuDTO) {
        log.info("creating menu items from menu {}", menuDTO);
        var menuItems = MenuUtil.getMenuItems(menuDTO);
        var created = service.createAll(menuItems);
        return MenuUtil.getMenuTo(created.get(0).getRestaurant(), created.get(0).getDateOf(), created);
    }

    protected MenuDTO get(int restaurantId, LocalDate dateParam) {
        var date = dateParam == null ? LocalDate.now() : dateParam;
        log.debug("getting items for restaurant {} at {}", restaurantId, dateParam);
        var items = service.getAll(restaurantId, date, date);

        return MenuUtil.getMenuTos(items).get(0);
    }

    protected List<MenuDTO> getAll(LocalDate start, LocalDate end) {
        var startDate = DateTimeUtil.getMinIfNull(start);
        var endDate = DateTimeUtil.getMaxIfNull(end);
        log.info("getting menus of all the restaurants between dates {} and {}", startDate, endDate);
        var menuItems = service.getAll(startDate, endDate);
        return MenuUtil.getMenuTos(menuItems);
    }

    protected List<MenuDTO> getAll(int restaurantId, LocalDate start, LocalDate end) {
        var startDate = DateTimeUtil.getMinIfNull(start);
        var endDate = DateTimeUtil.getMaxIfNull(end);
        log.info("getting menus of restaurant {} between dates {} and {}", restaurantId, startDate, endDate);
        var menuItems = service.getAll(restaurantId, start, end);
        return MenuUtil.getMenuTos(menuItems);
    }

    protected void update(MenuDTO menuDTO) {
        log.info("updating menu {}", menuDTO);
        var menuItems = MenuUtil.getMenuItems(menuDTO);
        service.updateAllByReplacing(menuItems);
    }

    protected void delete(int restaurantId, LocalDate date) {
        log.info("deleting a menu of restaurant {} at {}", restaurantId, date);
        service.deleteAtDate(restaurantId, date);
    }
}
