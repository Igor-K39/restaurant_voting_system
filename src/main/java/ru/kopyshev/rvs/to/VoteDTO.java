package ru.kopyshev.rvs.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class VoteDTO extends BaseDTO {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer restaurantId;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    public VoteDTO(Integer id, Integer userId, Integer restaurantId, LocalDate date, LocalTime time) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = date;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VoteDTO voteDTO = (VoteDTO) o;
        return Objects.equals(userId, voteDTO.userId) && Objects.equals(restaurantId, voteDTO.restaurantId) && Objects.equals(date, voteDTO.date) && Objects.equals(time, voteDTO.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, restaurantId, date, time);
    }

    @Override
    public String toString() {
        return "VoteDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
