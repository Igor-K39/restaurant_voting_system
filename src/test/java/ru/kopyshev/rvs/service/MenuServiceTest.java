package ru.kopyshev.rvs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kopyshev.rvs.exception.IllegalRequestDataException;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.dto.menu.MenuDTO;
import ru.kopyshev.rvs.dto.menu.MenuUpdateDTO;

import java.util.List;

import static ru.kopyshev.rvs.testdata.MenuTestData.*;
import static ru.kopyshev.rvs.testdata.TestData.NOT_FOUND_ID;

public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void create() {
        MenuDTO actual = service.create(NEW_MENU_UPDATE_DTO);
        MENU_TO_MATCHER.assertMatch(actual, NEW_MENU_DTO);
        MENU_TO_MATCHER.assertMatch(service.get(actual.id()), NEW_MENU_DTO);
    }

    @Test
    void get() {
        MenuDTO actual = service.get(MENU_ID_1);
        MENU_TO_MATCHER.assertMatch(actual, MENU_DTO_1);
    }

    @Test
    void update() {
        service.update(UPDATED_MENU_UPDATE_DTO, MENU_ID_1);
        MenuDTO actual = service.get(MENU_ID_1);
        MENU_TO_MATCHER.assertMatch(actual, UPDATED_MENU_DTO);
    }

    @Test
    void delete() {
        service.delete(MENU_ID_1);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(MENU_ID_1));
    }

    @Test
    void getAll() {
        List<MenuDTO> actual = service.getAll();
        List<MenuDTO> expected = List.of(MENU_DTO_4, MENU_DTO_2, MENU_DTO_3, MENU_DTO_1);
        System.out.println(actual.get(0));
        System.out.println(expected.get(0));
        Assertions.assertIterableEquals(actual, expected);
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void updateNotConsistent() {
        MenuUpdateDTO menuUpdateDTO = new MenuUpdateDTO(UPDATED_MENU_UPDATE_DTO);
        menuUpdateDTO.setId(MENU_ID_1);
        Assertions.assertThrows(IllegalRequestDataException.class, () ->
                service.update(menuUpdateDTO, NOT_FOUND_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }
}
