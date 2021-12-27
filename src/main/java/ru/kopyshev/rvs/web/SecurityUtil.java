package ru.kopyshev.rvs.web;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kopyshev.rvs.AuthorizedUser;

import java.util.Objects;

@UtilityClass
public class SecurityUtil {

    public static int authUserId() {
        return get().getId();
    }

    public static AuthorizedUser get() {
        return Objects.requireNonNull(safeGet(), "No authorized user found");
    }

    public static AuthorizedUser safeGet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }
}
