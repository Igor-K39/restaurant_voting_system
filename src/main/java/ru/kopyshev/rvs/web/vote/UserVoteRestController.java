package ru.kopyshev.rvs.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kopyshev.rvs.AuthorizedUser;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.dto.VoteDTO;
import ru.kopyshev.rvs.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserVoteRestController.USER_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteRestController {
    public static final String USER_REST_URL = "/rest/votes";

    private final VoteService voteService;

    public UserVoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public VoteDTO voteUp(@RequestParam("restaurant") Integer restaurantId) {
        AuthorizedUser authorizedUser = SecurityUtil.get();
        log.info("voting up {} for restaurant {} by user {}", LocalDateTime.now(), restaurantId, authorizedUser);
        return voteService.voteUp(authorizedUser.getUser(), restaurantId);
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelVote() {
        int userId = SecurityUtil.authUserId();
        log.info("cancelling vote at {} of user {}", LocalDateTime.now(), userId);
        voteService.cancelVote(userId);
    }

    @GetMapping("/of")
    public VoteDTO getOnDate(@RequestParam LocalDate date) {
        int userId = SecurityUtil.authUserId();
        return voteService.get(userId, date);
    }

    @GetMapping
    public List<VoteDTO> getAll(@RequestParam(name = "start", required = false) LocalDate start,
                                @RequestParam(name = "end", required = false) LocalDate end) {
        int userId = SecurityUtil.authUserId();
        log.info("getting all the votes of user {} between dates {} and {}", userId, start, end);
        return voteService.getAll(userId, start, end);
    }
}
