package ru.kopyshev.rvs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.Menu;
import ru.kopyshev.rvs.repository.CrudMenuRepository;
import ru.kopyshev.rvs.to.MenuDTO;
import ru.kopyshev.rvs.to.MenuUpdateDTO;
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

    private final CrudMenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public MenuService(CrudMenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Transactional
    public MenuDTO create(MenuUpdateDTO menuDTO) {
        ValidationUtil.checkNew(menuDTO);
        Menu menu = menuMapper.getEntity(menuDTO);
        Menu saved = menuRepository.save(menu);
        return menuMapper.getDTO(saved);
    }

    public MenuDTO get(int id) {
        Menu menu = menuRepository.get(id).orElseThrow(() -> new NotFoundException("Not found MenuItem with id = " + id));
        return menuMapper.getDTO(menu);
    }

    public List<MenuDTO> getAll() {
        return getAll(null, null);
    }

    public List<MenuDTO> getAll(LocalDate start, LocalDate end) {
        log.debug("Getting menu items between {} and {}", start, end);
        List<Menu> menus = menuRepository.getAll(getMinIfNull(start), getMaxIfNull(end));
        return !isNull(menus)
                ? menuMapper.getDTO(menus)
                : List.of();
    }

    public List<MenuDTO> getAll(int restaurantId, LocalDate start, LocalDate end) {
        log.debug("Getting menu items of restaurant {} between {} and {}", restaurantId, start, end);
        List<Menu> menuItems = menuRepository.getAll(restaurantId, getMinIfNull(start), getMaxIfNull(end));
        return !isNull(menuItems)
                ? menuMapper.getDTO(menuItems)
                : List.of();
    }

    public void update(MenuUpdateDTO menuUpdateDTO, int id) {
        ValidationUtil.assureIdConsistent(menuUpdateDTO, id);
        menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found menu with id = " + id));
        Menu menu = menuMapper.getEntity(menuUpdateDTO);
        menuRepository.update(menu);
    }

    public void delete(int id) {
        int affectedRows = menuRepository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, id);
    }
}