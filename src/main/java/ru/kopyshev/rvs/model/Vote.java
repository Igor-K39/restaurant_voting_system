package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vote")
@Access(AccessType.FIELD)
public class Vote extends BaseEntity {

    @NotNull
    @Column(name = "date_of")
    private LocalDate date;

    @NotNull
    @Column(name = "time_of")
    private LocalTime time;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", user=" + user.id +
                ", restaurant=" + restaurant.id +
                '}';
    }
}