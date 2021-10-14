package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class Vote extends BaseEntity {

    private LocalDate date;

    private LocalTime time;

    private User user;

    private Restaurant restaurant;

    public Vote(Vote v) {
        this(v.id, v.date, v.time, v.user, v.restaurant);
    }

    public Vote(User user, Restaurant restaurant) {
        this(null, LocalDate.now(), LocalTime.now(), user, restaurant);
    }

    public Vote(Integer id, LocalDate date, LocalTime time, User user, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.time = time;
        this.user = user;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}