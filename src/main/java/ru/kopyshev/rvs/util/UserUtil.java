package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.to.UserTo;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserUtil {

    public static User getUserFromTo(UserTo to) {
        return new User(to.getId(), to.getName(), to.getEmail(),
                to.getPassword(), to.isEnabled(), to.getRegistered(), to.getRoles());
    }

    public static UserTo getToFromUser(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(),
                user.getPassword(), user.isEnabled(), user.getRegistered(), user.getRoles());
    }

    public static List<UserTo> getToFromUser(List<User> users) {
        List<UserTo> tos = new ArrayList<>();
        for (User user : users) {
            tos.add(getToFromUser(user));
        }
        return tos;
    }
}
