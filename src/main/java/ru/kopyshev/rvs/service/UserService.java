package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.repository.CrudUserRepository;
import ru.kopyshev.rvs.util.CollectionUtil;
import ru.kopyshev.rvs.util.ValidationUtil;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final CrudUserRepository userRepository;

    public UserService(CrudUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        ValidationUtil.checkNew(user);
        return userRepository.save(user);
    }

    public User get(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found User with id: " + id));
    }

    public void update(Map<String, Object> patch, int id) {
        userRepository.patch(patch, id);
    }

    public void delete(int id) {
        int affectedRows = userRepository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, id);
    }

    public List<User> getAll() {
        return CollectionUtil.getImmutableListIfNull(userRepository.getAll());
    }

    public User getByEmail(String email) {
        return ValidationUtil.checkNotFound(userRepository.getByEmail(email), "email = " + email);
    }
}
