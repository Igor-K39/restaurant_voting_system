package ru.kopyshev.rvs.web;

import ru.kopyshev.rvs.model.BaseEntity;

public class SecurityUtil {
    private static int id = BaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}
