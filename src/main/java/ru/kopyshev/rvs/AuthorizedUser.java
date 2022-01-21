package ru.kopyshev.rvs;

import lombok.ToString;
import ru.kopyshev.rvs.dto.user.UserDTO;

@ToString(callSuper = true)
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private UserDTO user;

    public AuthorizedUser(UserDTO user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public void update(UserDTO userDTO) {
        this.user = new UserDTO(userDTO);
    }

    public UserDTO getUser() {
        return user;
    }
}
