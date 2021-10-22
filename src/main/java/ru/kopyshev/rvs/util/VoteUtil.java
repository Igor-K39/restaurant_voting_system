package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.Vote;
import ru.kopyshev.rvs.to.VoteTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteTo getToFromVote(Vote vote) {
        return new VoteTo(
                vote.id(),
                vote.getUser().id(),
                vote.getRestaurant().id(),
                LocalDateTime.of(vote.getDate(), vote.getTime())
        );
    }

    public static List<VoteTo> getToFromVote(List<Vote> votes) {
        List<VoteTo> tos = new ArrayList<>();
        for (Vote vote : votes) {
            tos.add(getToFromVote(vote));
        }
        return tos;
    }
}
