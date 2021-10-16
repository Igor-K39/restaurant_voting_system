package ru.kopyshev.rvs.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.model.Dish;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @EntityGraph(value = "with-restaurant")
    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.restaurant.id = :restaurant_id")
    Optional<Dish> get(@Param("id") int id, @Param("restaurant_id") int restaurantId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id = :id AND d.restaurant.id = :restaurant_id")
    int delete(@Param("id") int id, @Param("restaurant_id") int restaurant_id);

    @EntityGraph(value = "with-restaurant")
    @Query("SELECT d FROM Dish d ORDER BY d.restaurant.id, d.name")
    List<Dish> getAll();

    @EntityGraph(value = "with-restaurant")
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurant_id ORDER BY d.name")
    List<Dish> getAll(@Param("restaurant_id") int restaurantId);
}
