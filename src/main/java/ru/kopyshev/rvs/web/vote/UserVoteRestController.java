package ru.kopyshev.rvs.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kopyshev.rvs.service.RestaurantService;
import ru.kopyshev.rvs.service.UserService;
import ru.kopyshev.rvs.service.VoteService;
import ru.kopyshev.rvs.to.VoteTo;
import ru.kopyshev.rvs.util.DateTimeUtil;
import ru.kopyshev.rvs.util.VoteUtil;
import ru.kopyshev.rvs.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = UserVoteRestController.USER_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteRestController {
    public static final String USER_REST_URL = "/rest/votes";
    private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private final VoteService voteService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    public UserVoteRestController(VoteService voteService, UserService userService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void voteUp(@RequestParam("restaurant") Integer restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("voting up {} for restaurant {} by user {}", LocalDateTime.now(), restaurantId, userId);
        voteService.voteUp(userService.get(userId), restaurantService.get(restaurantId));
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelVote() {
        int userId = SecurityUtil.authUserId();
        log.info("cancelling vote at {} of user {}", LocalDateTime.now(), userId);
        voteService.cancelVote(userId);
    }

    @GetMapping
    public List<VoteTo> getAll(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        int userId = SecurityUtil.authUserId();
        var startDate = DateTimeUtil.getMinIfNull(start);
        var endDate = DateTimeUtil.getMaxIfNull(end);
        log.info("getting all the votes of user {} between dates {} and {}", userId, startDate, endDate);
        return VoteUtil.getToFromVote(voteService.getAll(userId, startDate, endDate));
    }
}