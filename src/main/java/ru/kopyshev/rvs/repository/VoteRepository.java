package ru.kopyshev.rvs.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.domain.Restaurant;
import ru.kopyshev.rvs.domain.User;
import ru.kopyshev.rvs.domain.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id = :id")
    int delete(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.date = :date AND v.user.id = :user_id")
    int delete(@Param("date") LocalDate date, @Param("user_id") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :user_id AND v.date >= :start AND v.date <= :end")
    Optional<List<Vote>> getBetweenDates(@Param("user_id") int userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :user_id AND v.date = :date")
    Optional<Vote> getVoteOfUserByDate(@Param("user_id") int userid, @Param("date") LocalDate date);

    @Query("SELECT count(v) FROM Vote v WHERE v.user.id = :user_id AND v.date = :date")
    int countVotesOfUserAtDate(@Param("user_id") int user_id, @Param("date") LocalDate date);

    default boolean hasSingleVote(int userId, LocalDate date) {
        int count = countVotesOfUserAtDate(userId, date);
        if (count > 1) {
            String message = "There must a single vote of user " + userId + " at " + date + ", found: " + count;
            throw new DataIntegrityViolationException(message);
        }
        return count != 0;
    }

    @Modifying
    @Transactional
    default Vote voteUp(User user, Restaurant restaurant) {
        Vote vote = getVoteOfUserByDate(user.id(), LocalDate.now()).orElse(new Vote());
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        vote.setDateTime(LocalDateTime.now());
        return save(vote);
    }
}
