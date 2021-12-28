package ru.kopyshev.rvs;

import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.model.Vote;
import ru.kopyshev.rvs.to.VoteDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_1;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_2;
import static ru.kopyshev.rvs.UserTestData.ADMIN;
import static ru.kopyshev.rvs.UserTestData.USER;

public class VoteTestData {
    public static final
    Matcher<Vote> VOTE_MATCHER = usingIgnoreFieldComparator(Vote.class, "restaurant", "time", "user");
    public static final Matcher<VoteDTO> VOTE_TO_MATCHER = usingIgnoreFieldComparator(VoteDTO.class);
    public static final int VOTE_1_ID = 100_016;
    public static final int VOTE_2_ID = 100_017;
    public static final int VOTE_3_ID = 100_018;
    public static final int VOTE_4_ID = 100_019;

    public static final
    Vote VOTE_1 = new Vote(VOTE_1_ID, LocalDate.parse("2021-03-01"), LocalTime.parse("09:00:00"), USER, RESTAURANT_1);
    public static final
    Vote VOTE_2 = new Vote(VOTE_2_ID, LocalDate.parse("2021-03-01"), LocalTime.parse("09:00:00"), ADMIN, RESTAURANT_1);
    public static final
    Vote VOTE_3 = new Vote(VOTE_3_ID, LocalDate.parse("2021-03-02"), LocalTime.parse("10:00:00"), USER, RESTAURANT_2);
    public static final
    Vote VOTE_4 = new Vote(VOTE_4_ID, LocalDate.parse("2021-03-02"), LocalTime.parse("10:00:00"), ADMIN, RESTAURANT_2);
}
