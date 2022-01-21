package ru.kopyshev.rvs.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
}
