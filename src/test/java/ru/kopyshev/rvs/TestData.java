package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class TestData {
    public static final int NOT_FOUND_ID = 200_000;
    public static final LocalDate DATE_1 = LocalDate.of(2021, 3, 1);
    public static final LocalDate DATE_2 = LocalDate.of(2021, 3, 2);
}