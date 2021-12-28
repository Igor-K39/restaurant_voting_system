package ru.kopyshev.rvs.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class VoteDTO extends BaseDTO {

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer restaurantId;

    public VoteDTO(VoteDTO vote) {
        this(vote.id, vote.userId, vote.restaurantId, vote.dateTime);
    }

    public VoteDTO(Integer userId, Integer restaurantId, LocalDateTime dateTime){
        this(null, userId, restaurantId, dateTime);
    }

    public VoteDTO(Integer id, Integer userId, Integer restaurantId, LocalDateTime dateTime) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.dateTime = dateTime;
    }

    @JsonIgnore
    public LocalDate getLocalDate() {
        return dateTime.toLocalDate();
    }

    @JsonIgnore
    public LocalTime getLocalTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VoteDTO voteTo = (VoteDTO) o;
        return Objects.equals(dateTime, voteTo.dateTime) && Objects.equals(userId, voteTo.userId) && Objects.equals(restaurantId, voteTo.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateTime, userId, restaurantId);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "dateTime=" + dateTime +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
