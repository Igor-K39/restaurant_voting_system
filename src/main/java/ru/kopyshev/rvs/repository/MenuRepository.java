package ru.kopyshev.rvs.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.domain.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.menuItems WHERE m.id = :id")
    Optional<Menu> get(@Param("id") int id);

    @EntityGraph(value = "menuWithAllFields")
    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.menuItems WHERE m.dateOf >= :start AND m.dateOf <= :end " +
            "ORDER BY m.dateOf DESC, m.restaurant.id DESC, m.id DESC")
    List<Menu> getAll(@Param("start") LocalDate date, @Param("end") LocalDate end);

    @EntityGraph(value = "menuWithAllFields")
    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.menuItems " +
            "WHERE m.dateOf >= :start AND m.dateOf <= :end AND m.restaurant.id = :restaurant_id")
    List<Menu> getAll(@Param("restaurant_id") int restaurantId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Modifying
    @Transactional
    default void update(Menu menu) {
        deleteMenuItems(menu.id());
        save(menu);
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuItem m WHERE m.menu.id = :id")
    void deleteMenuItems(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id = :id")
    int delete(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.dateOf = :date_of AND m.restaurant.id = :id")
    int delete(@Param("id") int restaurantId, @Param("date_of") LocalDate dateOf);
}
