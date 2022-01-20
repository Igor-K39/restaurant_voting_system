package ru.kopyshev.rvs;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.to.UserTo;
import ru.kopyshev.rvs.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, user.getRoles());
        this.userTo = UserUtil.getToFromUser(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = new UserTo(newTo);
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
