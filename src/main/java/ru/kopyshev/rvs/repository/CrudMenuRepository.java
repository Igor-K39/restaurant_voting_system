package ru.kopyshev.rvs.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.model.MenuItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<MenuItem, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuItem m WHERE m.id = :id")
    int delete(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuItem m WHERE m.dateOf = :date_of AND m.restaurant.id = :id")
    int delete(@Param("id") int restaurantId, @Param("date_of") LocalDate dateOf);

    @EntityGraph(value = "menu_item_full")
    @Query("SELECT m FROM MenuItem m WHERE m.id = :id")
    Optional<MenuItem> get(@Param("id") int id);

    @EntityGraph(value = "menu_item_full")
    @Query("SELECT m FROM MenuItem m ORDER BY m.dateOf DESC")
    List<MenuItem> getAll();

    @EntityGraph(value = "menu_item_full")
    @Query("SELECT m FROM MenuItem m WHERE m.dateOf >= :start AND m.dateOf <= :end " +
            "ORDER BY m.dateOf DESC, m.restaurant.id DESC, m.id DESC")
    List<MenuItem> getAll(@Param("start") LocalDate date, @Param("end") LocalDate end);

    @EntityGraph(value = "menu_item_full")
    @Query("SELECT m FROM MenuItem m WHERE m.dish.restaurant.id = :id ORDER BY m.dateOf DESC")
    List<MenuItem> getAll(@Param("id") int restaurantId);

    @EntityGraph(value = "menu_item_full")
    @Query("SELECT m FROM MenuItem m WHERE m.dateOf >= :start AND m.dateOf <= :end AND m.dish.restaurant.id = :id")
    List<MenuItem> getAll(@Param("id") int restaurantId, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
