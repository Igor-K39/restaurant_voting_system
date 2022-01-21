package ru.kopyshev.rvs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.AuthorizedUser;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.domain.User;
import ru.kopyshev.rvs.repository.UserRepository;
import ru.kopyshev.rvs.util.CollectionUtil;
import ru.kopyshev.rvs.util.ValidationUtil;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        log.debug("Creating a new user: {}", user);
        ValidationUtil.checkNew(user);
        return userRepository.save(user);
    }

    public User get(Integer id) {
        log.debug("Getting user with id {}", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, "id = " + id));
    }

    public void update(Map<String, Object> patch, Integer id) {
        log.debug("Patching a user with id {} by {}", id, patch);
        userRepository.patch(patch, id);
    }

    public void delete(Integer id) {
        log.debug("Deleting a user with id {}", id);
        int affectedRows = userRepository.delete(id);
        ValidationUtil.checkNotFoundWithId(affectedRows != 0, User.class, id);
    }

    public List<User> getAll() {
        log.debug("Getting all users");
        return CollectionUtil.getImmutableListIfNull(userRepository.getAll());
    }

    public User getByEmail(String email) {
        log.debug("Getting a user with email {}", email);
        return ValidationUtil.checkNotFound(userRepository.getByEmail(email), User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by username {}", email);
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
