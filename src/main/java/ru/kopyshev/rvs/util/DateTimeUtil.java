package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Objects;

@UtilityClass
public class DateTimeUtil {

    private static final LocalDate max = LocalDate.now().plusYears(300);

    private static final LocalDate min = LocalDate.now().minusYears(300);

    public static LocalDate getMaxDate() {
        return max;
    }

    public static LocalDate getMinDate() {
        return min;
    }

    public static LocalDate getMaxIfNull(LocalDate date) {
        return Objects.isNull(date) ? max : date;
    }

    public static LocalDate getMinIfNull(LocalDate date) {
        return Objects.isNull(date) ? min : date;
    }
}
