package ru.kopyshev.rvs;

import ru.kopyshev.rvs.MatcherFactory.Matcher;
import ru.kopyshev.rvs.to.VoteDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.kopyshev.rvs.MatcherFactory.usingIgnoreFieldComparator;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_1;
import static ru.kopyshev.rvs.RestaurantTestData.RESTAURANT_ID_2;
import static ru.kopyshev.rvs.UserTestData.ADMIN_ID;
import static ru.kopyshev.rvs.UserTestData.USER_ID;

public class VoteTestData {
    public static final Matcher<VoteDTO> VOTE_TO_MATCHER = usingIgnoreFieldComparator(VoteDTO.class, "time");
    public static final int VOTE_1_ID = 100_022;
    public static final int VOTE_2_ID = 100_023;
    public static final int VOTE_3_ID = 100_024;
    public static final int VOTE_4_ID = 100_025;

    public static final VoteDTO VOTE_DTO_1 =
            new VoteDTO(VOTE_1_ID, USER_ID, RESTAURANT_ID_1, LocalDate.parse("2021-03-01"), LocalTime.parse("09:00:00"));
    public static final VoteDTO VOTE_DTO_2 =
            new VoteDTO(VOTE_2_ID, ADMIN_ID, RESTAURANT_ID_1, LocalDate.parse("2021-03-01"), LocalTime.parse("09:00:00"));
    public static final VoteDTO VOTE_DTO_3 =
            new VoteDTO(VOTE_3_ID, USER_ID, RESTAURANT_ID_2, LocalDate.parse("2021-03-02"), LocalTime.parse("10:00:00"));
    public static final VoteDTO VOTE_DTO_4 =
            new VoteDTO(VOTE_4_ID, ADMIN_ID, RESTAURANT_ID_2, LocalDate.parse("2021-03-02"), LocalTime.parse("10:00:00"));
}
