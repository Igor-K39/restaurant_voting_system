package ru.kopyshev.rvs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.domain.Menu;
import ru.kopyshev.rvs.dto.menu.MenuDTO;
import ru.kopyshev.rvs.dto.menu.MenuUpdateDTO;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.repository.MenuRepository;
import ru.kopyshev.rvs.util.ValidationUtil;
import ru.kopyshev.rvs.util.mapper.MenuMapper;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;
import static ru.kopyshev.rvs.util.DateTimeUtil.getMaxIfNull;
import static ru.kopyshev.rvs.util.DateTimeUtil.getMinIfNull;


@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public MenuService(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @CacheEvict("menu.getAll")
    @Transactional
    public MenuDTO create(MenuUpdateDTO menuUpdateDTO) {
        log.debug("Creating a new menu: {}", menuUpdateDTO);
        ValidationUtil.checkNew(menuUpdateDTO);
        Menu menu = menuMapper.toEntity(menuUpdateDTO);
        return menuMapper.toDTO(menuRepository.save(menu));
    }

    @Cacheable("menu.get")
    public MenuDTO get(Integer id) {
        log.debug("Getting a menu with id {}", id);
        Menu menu = menuRepository.get(id).orElseThrow(() -> new NotFoundException(Menu.class, "id = " + id));
        return menuMapper.toDTO(menu);
    }

    @Cacheable("menu.getAll")
    public List<MenuDTO> getAll() {
        log.debug("Getting all existing menus");
        return getAll(null, null);
    }

    public List<MenuDTO> getAll(LocalDate start, LocalDate end) {
        log.debug("Getting all menus between {} and {}", start, end);
        List<Menu> menus = menuRepository.getAll(getMinIfNull(start), getMaxIfNull(end));
        return !isNull(menus)
                ? menuMapper.toDTO(menus)
                : List.of();
    }

    public List<MenuDTO> getAll(int restaurantId, LocalDate start, LocalDate end) {
        log.debug("Getting all menus of restaurant {} between {} and {}", restaurantId, start, end);
        List<Menu> menuItems = menuRepository.getAll(restaurantId, getMinIfNull(start), getMaxIfNull(end));
        return !isNull(menuItems)
                ? menuMapper.toDTO(menuItems)
                : List.of();
    }

    @Caching(evict = {
            @CacheEvict(value = "menu.getAll", allEntries = true),
            @CacheEvict(value = "menu.get")
    })
    public void update(MenuUpdateDTO menuUpdateDTO, Integer id) {
        log.debug("Updating menu {} by {}", id, menuUpdateDTO);
        ValidationUtil.assureIdConsistent(menuUpdateDTO, id);
        menuRepository.findById(id).orElseThrow(() -> new NotFoundException(Menu.class, "id = " + id));
        Menu menu = menuMapper.toEntity(menuUpdateDTO);
        menuRepository.update(menu);
    }

    @Caching(evict = {
            @CacheEvict(value = "menu.getAll", allEntries = true),
            @CacheEvict(value = "menu.get")
    })
    public void delete(Integer id) {
        log.debug("Deleting menu with id {}", id);
        int affectedRows = menuRepository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, Menu.class, id);
    }
}