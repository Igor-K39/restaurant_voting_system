package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;
import ru.kopyshev.rvs.model.Vote;
import ru.kopyshev.rvs.to.VoteDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteDTO getToFromVote(Vote vote) {
        return new VoteDTO(
                vote.id(),
                vote.getUser().id(),
                vote.getRestaurant().id(),
                LocalDateTime.of(vote.getDate(), vote.getTime())
        );
    }

    public static List<VoteDTO> getToFromVote(List<Vote> votes) {
        List<VoteDTO> tos = new ArrayList<>();
        for (Vote vote : votes) {
            tos.add(getToFromVote(vote));
        }
        return tos;
    }
}
