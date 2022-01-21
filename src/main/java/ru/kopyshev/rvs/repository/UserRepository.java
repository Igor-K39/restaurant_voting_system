package ru.kopyshev.rvs.repository;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.domain.User;

import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    int delete(@Param("id") int id);

    @Query("SELECT u FROM User u ORDER BY u.name, u.email")
    List<User> getAll();

    User getByEmail(String email);

    @Modifying
    @Transactional
    default void patch(Map<String, Object> patch, int id) {
        User stored = findById(id).orElseThrow(() -> new NotFoundException(User.class, "id = " + id));
        BeanWrapper wrapper = new BeanWrapperImpl(stored);
        wrapper.setPropertyValues(patch);
    }
}
