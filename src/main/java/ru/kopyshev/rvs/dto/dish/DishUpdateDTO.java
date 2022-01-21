package ru.kopyshev.rvs.dto.dish;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.kopyshev.rvs.dto.NamedDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class DishUpdateDTO extends NamedDTO {

    @NotNull
    @PositiveOrZero
    private Integer restaurantId;

    public DishUpdateDTO(Integer id, String name, Integer restaurantId) {
        super(id, name);
        this.restaurantId = restaurantId;
    }
}
