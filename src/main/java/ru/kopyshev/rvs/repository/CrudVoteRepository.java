package ru.kopyshev.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id = :id")
    int delete(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.date = :date AND v.user.id = :user_id")
    int delete(@Param("date") LocalDate date, @Param("user_id") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :user_id AND v.date >= :start AND v.date <= :end")
    List<Vote> getBetweenDates(@Param("user_id") int userId, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
