package ru.kopyshev.rvs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kopyshev.rvs.AuthorizedUser;
import ru.kopyshev.rvs.domain.User;
import ru.kopyshev.rvs.dto.user.UserDTO;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.repository.UserRepository;
import ru.kopyshev.rvs.util.mapper.UserMapper;

import java.util.List;
import java.util.Map;

import static ru.kopyshev.rvs.util.SecurityUtil.encodePassword;
import static ru.kopyshev.rvs.util.ValidationUtil.*;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO create(UserDTO userDTO) {
        log.debug("Creating a new user: {}", userDTO);
        checkNew(userDTO);
        User user = userMapper.toEntity(userDTO);
        encodePassword(user);
        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO get(Integer id) {
        log.debug("Getting user with id {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, "id = " + id));
        return userMapper.toDTO(user);
    }

    public void update(Map<String, Object> patch, Integer id) {
        log.debug("Patching a user with id {} by {}", id, patch);
        String password = (String) patch.get("password");
        patch.put("password", encodePassword(password));
        userRepository.patch(patch, id);
    }

    public void delete(Integer id) {
        log.debug("Deleting a user with id {}", id);
        int affectedRows = userRepository.delete(id);
        checkNotFoundWithId(affectedRows != 0, User.class, id);
    }

    public List<UserDTO> getAll() {
        log.debug("Getting all users");
        List<User> users = userRepository.getAll();
        return userMapper.toDTO(users);
    }

    public UserDTO getByEmail(String email) {
        log.debug("Getting a user with email {}", email);
        User user = userRepository.getByEmail(email);
        return userMapper.toDTO(checkNotFound(user, User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by username {}", email);
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(userMapper.toDTO(user));
    }
}
