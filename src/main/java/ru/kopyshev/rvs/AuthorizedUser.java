package ru.kopyshev.rvs;

import ru.kopyshev.rvs.domain.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public void update(User user) {
        this.user = new User(user);
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
