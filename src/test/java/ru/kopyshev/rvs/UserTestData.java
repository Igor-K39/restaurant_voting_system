package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.model.Role;
import ru.kopyshev.rvs.model.User;
import ru.kopyshev.rvs.to.UserTo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;

@UtilityClass
public class UserTestData {

    public static final Matcher<User> USER_MATCHER = usingIgnoreFieldComparator(User.class, "registered");
    public static final Matcher<UserTo> USER_TO_MATCHER = usingIgnoreFieldComparator(UserTo.class, "registered");

    public static final int USER_ID = 100_000;
    public static final int ADMIN_ID = 100_001;
    public static final
    User USER = new User(100_000, "User", "user.sd@gmail.com", "user_password", Set.of(Role.USER));
    public static final
    User ADMIN = new User(100_001, "Admin", "admin.sd@gmail.com", "admin_password", Set.of(Role.ADMIN));

    public static final String USER_UPDATED_NAME = "Updated Elk";
    public static final String USER_UPDATED_EMAIL = "updated.little.elk@gmail.com";
    public static final String USER_UPDATED_PASSWORD = "UpdatedElksword";
    public static final Set<Role> USER_UPDATED_ROLES = Set.of(Role.USER, Role.ADMIN);

    public static User getNew() {
        User aNew = new User();
        aNew.setName("Little Elk");
        aNew.setEmail("little.elk@gmail.com");
        aNew.setPassword("elksword");
        aNew.setRoles(Set.of(Role.USER));
        return aNew;
    }

    public static User getUpdated(User user) {
        User updated = new User(user);
        updated.setName(USER_UPDATED_NAME);
        updated.setEmail(USER_UPDATED_EMAIL);
        updated.setPassword(USER_UPDATED_PASSWORD);
        updated.setRoles(USER_UPDATED_ROLES);
        updated.setEnabled(false);
        return updated;
    }

    public static Map<String, Object> getMapWithUpdates() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", USER_UPDATED_NAME);
        updates.put("email", USER_UPDATED_EMAIL);
        updates.put("password", USER_UPDATED_PASSWORD);
        updates.put("roles", USER_UPDATED_ROLES);
        updates.put("enabled", false);
        return updates;
    }
}