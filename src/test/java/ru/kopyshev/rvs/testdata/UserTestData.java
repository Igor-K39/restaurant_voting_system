package ru.kopyshev.rvs.testdata;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.domain.Role;
import ru.kopyshev.rvs.domain.User;
import ru.kopyshev.rvs.dto.user.UserDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;

@UtilityClass
public class UserTestData {

    public static final Matcher<UserDTO> USER_TO_MATCHER = usingIgnoreFieldComparator(UserDTO.class, "password", "registered");

    public static final int USER_ID = 100_000;
    public static final int ADMIN_ID = 100_001;
    public static final String USER_PASSWORD = "user_password";
    public static final String ADMIN_PASSWORD = "admin_password";

    public static final
    UserDTO USER = new UserDTO(100_000, "User", "user.sd@gmail.com", USER_PASSWORD, true, new Date(), Set.of(Role.USER));
    public static final
    UserDTO ADMIN = new UserDTO(100_001, "Admin", "admin.sd@gmail.com", ADMIN_PASSWORD, true, new Date(), Set.of(Role.ADMIN));

    public static final
    User USER_AUTH = new User(100_000, "User", "user.sd@gmail.com", USER_PASSWORD, Set.of(Role.USER));
    public static final
    User ADMIN_AUTH = new User(100_001, "Admin", "admin.sd@gmail.com", ADMIN_PASSWORD, Set.of(Role.ADMIN));

    public static final String USER_UPDATED_NAME = "Updated Elk";
    public static final String USER_UPDATED_EMAIL = "updated.little.elk@gmail.com";
    public static final String USER_UPDATED_PASSWORD = "UpdatedElksword";
    public static final Set<Role> USER_UPDATED_ROLES = Set.of(Role.USER, Role.ADMIN);

    public static UserDTO getNew() {
        UserDTO aNew = new UserDTO();
        aNew.setName("Little Elk");
        aNew.setEmail("little.elk@gmail.com");
        aNew.setPassword("elksword");
        aNew.setRoles(Set.of(Role.USER));
        return aNew;
    }

    public static UserDTO getUpdated(UserDTO user) {
        UserDTO updated = new UserDTO(user);
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